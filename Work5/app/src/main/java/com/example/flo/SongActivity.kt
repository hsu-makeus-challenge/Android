package com.example.flo

import android.content.SharedPreferences
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

    // SharedPreferences 사용
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SharedPreferences 초기화
        sharedPreferences = getSharedPreferences("SongPrefs", MODE_PRIVATE)

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
            PlayerState.progress = 0      // 0초로 초기화
            handler.removeCallbacks(runnable)        // 이전 스레드 제거
            if (isPlaying) handler.post(runnable)    // 재생 중이면 다시 시작
        }

        // SeekBar 진행 상태 변경 시 SharedPreferences에 저장
        binding.songProgressSb.setOnSeekBarChangeListener(object :
            android.widget.SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: android.widget.SeekBar?, progress: Int, fromUser: Boolean) {
                // 진행 상태를 SharedPreferences에 저장
                if (fromUser) {
                    val editor = sharedPreferences.edit()
                    editor.putInt("songProgress", progress)
                    editor.apply()
                }
            }

            override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {}
        })

        // SharedPreferences에서 진행 상태 읽어서 SeekBar에 적용
        val progress = sharedPreferences.getInt("songProgress", 0)
        binding.songProgressSb.progress = progress

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
                        // 진행 상태를 SharedPreferences에 저장
                        val editor = sharedPreferences.edit()
                        editor.putInt("songProgress", newProgress)
                        editor.apply()
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
