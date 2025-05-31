package com.example.feelingapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.reflect.KProperty

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val imageButton = findViewById<ImageButton>(R.id.imageButton)
        val imageButton2 = findViewById<ImageView>(R.id.imageButton2)
        val imageButton3 = findViewById<ImageButton>(R.id.imageButton3)
        val imageButton4 = findViewById<ImageButton>(R.id.imageButton4)
        val imageButton5 = findViewById<ImageButton>(R.id.imageButton5)

        //페이지 이동
        fun moveToAnotherPage1() {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        fun moveToAnotherPage2() {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }

        fun moveToAnotherPage3() {
            val intent = Intent(this, MainActivity4::class.java)
            startActivity(intent)
        }

        fun moveToAnotherPage4() {
            val intent = Intent(this, MainActivity5::class.java)
            startActivity(intent)
        }

        fun moveToAnotherPage5() {
            val intent = Intent(this, MainActivity6::class.java)
            startActivity(intent)
        }

        //함수 호출
        imageButton.setOnClickListener {
            moveToAnotherPage1()
        }
        imageButton2.setOnClickListener {
            moveToAnotherPage2()
        }
        imageButton3.setOnClickListener {
            moveToAnotherPage3()
        }
        imageButton4.setOnClickListener {
            moveToAnotherPage4()
        }
        imageButton5.setOnClickListener {
            moveToAnotherPage5()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

