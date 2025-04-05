package com.example.floclone

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.floclone.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    lateinit var binding: ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.songMusicTitleTv.setText(intent.getStringExtra("title"))
        binding.songSingerNameTv.setText(intent.getStringExtra("singer"))

        binding.songDownIb.setOnClickListener(){
            val resultIntent = Intent().apply {
                putExtra("title", binding.songMusicTitleTv.text.toString())
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }

    }
}