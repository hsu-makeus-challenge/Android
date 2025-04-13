package com.example.study3

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class HomeActivity : AppCompatActivity() {
    private lateinit var seekBar: SeekBar
    private lateinit var timeTextView: TextView
    private lateinit var playPauseImage: ImageView
    private lateinit var likeunlikeImage : ImageView
    private lateinit var nextImage: ImageView
    private lateinit var prevImage: ImageView
    private lateinit var mainImage :ImageView
    private lateinit var chtext : TextView

    private var time = 0L
    private var isRunning = false
    private var job: Job? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        playPauseImage = findViewById(R.id.imageView12) // 시작 버튼 이미지
        timeTextView = findViewById(R.id.timeTextView)
        seekBar = findViewById(R.id.seekBar)
        likeunlikeImage = findViewById(R.id.imagelike)
        nextImage = findViewById(R.id.imageView10)
        prevImage = findViewById(R.id.imageView13)
        mainImage = findViewById(R.id.imageView9)
        chtext = findViewById(R.id.textView14)

        nextImage.setOnClickListener{
            mainImage.setImageResource(R.drawable.v4)
            chtext.setText("Fujin")
            likeunlikeImage.setImageResource(R.drawable.ic_my_like_off)

            clearTimer()

        }
       // prevImage.setOnClickListener{
         //   mainImage.set
        //}

        // SeekBar 최대값 설정
        seekBar.max = 180
        seekBar.progress = 0

        var imageIndex=0;

        likeunlikeImage.setOnClickListener{
            if(imageIndex == 0){
                likeunlikeImage.setImageResource(R.drawable.ic_my_like_on)
                imageIndex=1
            }
           else if(imageIndex ==1 ){
                likeunlikeImage.setImageResource(R.drawable.ic_my_like_off)
                imageIndex=0
            }
        }

        playPauseImage.setOnClickListener {
            if (!isRunning) {
                startTimer()
                playPauseImage.setImageResource(R.drawable.btn_miniplay_mvpause)
                 // 일시정지 버튼 이미지로 변경
            } else {
                pauseTimer()
                playPauseImage.setImageResource(R.drawable.btn_miniplay_mvplay)
               // 시작 버튼 이미지로 변경
            }
        }

        // "back" 이미지 뷰 클릭 리스너 설정
        val backImageView: ImageView = findViewById(R.id.back)
        backImageView.setOnClickListener {
            // MainActivity로 돌아가기
            finish() // 현재 Activity 종료
        }
    }

    private fun startTimer() {
        isRunning = true
        job = CoroutineScope(Dispatchers.Main).launch {
            while (isRunning) {
                delay(1000)
                time += 1
                timeTextView.text = formatTime(time)
                seekBar.progress = time.toInt() // SeekBar 진행률 업데이트
            }
        }
    }

    private fun pauseTimer() {
        isRunning = false
        job?.cancel()
    }
    private fun clearTimer() {
        if (isRunning) {
            time = 0L
            timeTextView.text = formatTime(time)
            seekBar.progress = 0
        } else {
            pauseTimer()
            time = 0L
            timeTextView.text = formatTime(time)
            seekBar.progress = 0
        }
    }

    private fun formatTime(seconds: Long): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, secs)
    }
}
