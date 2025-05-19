package com.example.flo

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.flo.databinding.ActivitySongBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SongActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongBinding
    private var isPlaying = false
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private lateinit var songDB: SongDatabase
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        songDB = SongDatabase.getInstance(this)
        sharedPreferences = getSharedPreferences("SongPrefs", MODE_PRIVATE)

        binding.likeBtnIv.setOnClickListener {
            val song = Songs(
                title = "현재 곡 제목",
                singer = "현재 가수 이름",
                coverImg = R.drawable.img_album_exp
            )
            lifecycleScope.launch(Dispatchers.IO) {
                songDB.songDao().insert(song)
            }
        }

        binding.songDownIb.setOnClickListener {
            finish()
        }

        binding.startBtnIv.setOnClickListener {
            setPlayerStatus(false)
        }

        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(true)
        }

        binding.repeatBtnIv.setOnClickListener {
            PlayerState.progress = 0
            handler.removeCallbacks(runnable)
            if (isPlaying) handler.post(runnable)
        }

        binding.songProgressSb.setOnSeekBarChangeListener(object : android.widget.SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: android.widget.SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    sharedPreferences.edit().putInt("songProgress", progress).apply()
                }
            }

            override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {}
        })

        binding.songProgressSb.progress = sharedPreferences.getInt("songProgress", 0)

        initSeekBar()
    }

    private fun initSeekBar() {
        runnable = object : Runnable {
            override fun run() {
                if (isPlaying) {
                    val newProgress = binding.songProgressSb.progress + 1000
                    if (newProgress <= binding.songProgressSb.max) {
                        binding.songProgressSb.progress = newProgress
                        sharedPreferences.edit().putInt("songProgress", newProgress).apply()
                        handler.postDelayed(this, 1000)
                    } else {
                        binding.songProgressSb.progress = 0
                        setPlayerStatus(true)
                    }
                }
            }
        }
    }

    private fun setPlayerStatus(isPause: Boolean) {
        if (isPause) {
            binding.startBtnIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            isPlaying = false
            handler.removeCallbacks(runnable)
        } else {
            binding.startBtnIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            isPlaying = true
            handler.post(runnable)
        }
    }
}
