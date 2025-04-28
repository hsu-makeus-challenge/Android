package com.example.mission4

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var timerTextView: TextView
    private lateinit var startButton: Button
    private lateinit var pauseButton: Button
    private lateinit var clearButton: Button

    private var isRunning = false
    private var elapsedTime = 0L
    private var coroutineJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //상태 표시줄 색상 변경
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.parseColor("#E6E6E6")
        }

        timerTextView = findViewById(R.id.timerTextView)
        startButton = findViewById(R.id.startButton)
        pauseButton = findViewById(R.id.pauseButton)
        clearButton = findViewById(R.id.clearButton)

        // 초기 타이머 설정
        timerTextView.text = "00:00,00"

        startButton.setOnClickListener {
            startTimer()
        }

        pauseButton.setOnClickListener {
            pauseTimer()
        }

        clearButton.setOnClickListener {
            clearTimer()
        }
    }

    private fun startTimer() {
        if (!isRunning) {
            isRunning = true
            startButton.visibility = View.GONE
            pauseButton.visibility = View.VISIBLE

            // 타이머가 시작될 때 텍스트 형식을 변경
            timerTextView.text = formatTime(elapsedTime) // 초기 값 표시

            coroutineJob = CoroutineScope(Dispatchers.Main).launch {
                while (isRunning) {
                    elapsedTime += 10 // 10ms 단위로 증가
                    timerTextView.text = formatTime(elapsedTime)
                    delay(10) // 10ms마다 업데이트
                }
            }
        }
    }

    private fun pauseTimer() {
        isRunning = false
        startButton.visibility = View.VISIBLE
        pauseButton.visibility = View.GONE
        coroutineJob?.cancel() // 코루틴 중지
    }

    private fun clearTimer() {
        isRunning = false
        elapsedTime = 0L
        timerTextView.text = "00:00.00" // 초기화 시 업데이트 (이제는 점(.) 사용)
        startButton.visibility = View.VISIBLE
        pauseButton.visibility = View.GONE
        coroutineJob?.cancel() // 코루틴 중지
    }

    private fun formatTime(timeInMillis: Long): String {
        val milliseconds = (timeInMillis % 1000) / 10 //0.01초 단위로 표시
        val seconds = (timeInMillis / 1000) % 60
        val minutes = (timeInMillis / (1000 * 60)) % 60

        return String.format("%02d:%02d.%02d", minutes, seconds, milliseconds) // 100ms 단위로 표시
    }
}


