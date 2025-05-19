package com.example.timer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.timer.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var timeMillis = 0L
    private var isRunning = false
    private var job: Job? = null
    private var isPaused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            if (!isRunning) {
                startStopwatch()
                binding.btnStart.text = "Pause"
            } else {
                pauseStopwatch()
                binding.btnStart.text = "Start"
            }
        }

        // 초기화 버튼
        binding.btnClear.setOnClickListener {
            clearStopwatch()
        }
    }

    private fun startStopwatch() {
        isRunning = true
        isPaused = false
        job = CoroutineScope(Dispatchers.Main).launch {
            val startTime = System.currentTimeMillis() - timeMillis
            while (isActive) {
                timeMillis = System.currentTimeMillis() - startTime
                updateTimerText(timeMillis)
                delay(10)
            }
        }
    }

    private fun pauseStopwatch() {
        isRunning = false
        isPaused = true
        job?.cancel()
    }

    private fun clearStopwatch() {
        if (isRunning) {
            job?.cancel()
            isRunning = false
            timeMillis = 0L
            updateTimerText(timeMillis)
            binding.btnStart.text = "Start"
        } else {
            timeMillis = 0L
            updateTimerText(timeMillis)
            binding.btnStart.text = "Start"
            isPaused = false
        }
    }

    private fun updateTimerText(ms: Long) {
        val seconds = (ms / 1000) % 60
        val minutes = (ms / 1000) / 60
        val centiSeconds = (ms % 1000) / 10
        binding.tvTimer.text = String.format(Locale.getDefault(), "%02d:%02d,%02d", minutes, seconds, centiSeconds)
    }
}
