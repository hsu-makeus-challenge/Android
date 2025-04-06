package com.example.week1_remind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.week1_remind.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding   //ViewBinding 객체
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ViewBinding 초기화
        binding = ActivityMainBinding.inflate(layoutInflater)

        //setContentView를 binding.root로 설정
        setContentView(binding.root)

        //findViewByid 대신 binding을 사용
        binding.yellowFace.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        /*
        val yellowFaceButton = findViewById<ImageButton>(R.id.yellow_face)
        yellowFaceButton.setOnClickListener{
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
        */

    }
}