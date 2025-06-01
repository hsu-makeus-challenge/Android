package com.example.flo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding
    private var isPlaying = false
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.songDownIb.setOnClickListener {
            finish()
        }

        binding.startBtnIv.setOnClickListener {
            setPlayerStatus(false)
        }

        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(true)
        }

        // 초기화
        initSeekBar()
    }

    private fun initSeekBar() {
        runnable = object : Runnable {
            override fun run() {
                if (isPlaying) {
                    val newProgress = binding.songProgressSb.progress + 1000
                    if (newProgress <= binding.songProgressSb.max) {
                        binding.songProgressSb.progress = newProgress
                        handler.postDelayed(this, 1000)
                    } else {
                        binding.songProgressSb.progress = 0
                        setPlayerStatus(true)
                    }
                }
            }
        }
    }

    fun setPlayerStatus(isPause: Boolean) {
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
