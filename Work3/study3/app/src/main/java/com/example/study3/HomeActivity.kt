package com.example.study3

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

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

        // "back" 이미지 뷰 클릭 리스너 설정
        val backImageView: ImageView = findViewById(R.id.back) // "back" 이미지 뷰의 ID에 맞게 수정

        backImageView.setOnClickListener {
            // MainActivity로 돌아가기
            finish() // 현재 Activity 종료
        }
    }

}
