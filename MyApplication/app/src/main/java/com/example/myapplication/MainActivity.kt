package com.example

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.study1.Angry
import com.example.study1.Excitement
import com.example.study1.Happy
import com.example.study1.Nervous
import com.example.study1.Soso
import com.example.study1.R

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView2.setOnClickListener {
            val intent = Intent(this, Happy::class.java)
            startActivity(intent)
        }

        binding.imageView3.setOnClickListener {
            val intent = Intent(this, Excitement::class.java)
            startActivity(intent)
        }

        binding.imageView4.setOnClickListener {
            val intent = Intent(this, Soso::class.java)
            startActivity(intent)
        }

        binding.imageView5.setOnClickListener {
            val intent = Intent(this, Nervous::class.java)
            startActivity(intent)
        }

        binding.imageView6.setOnClickListener {
            val intent = Intent(this, Angry::class.java)
            startActivity(intent)
        }
    }
}