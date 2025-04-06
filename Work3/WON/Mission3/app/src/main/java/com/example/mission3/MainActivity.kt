package com.example.mission3

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mission3.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var bottomNavi: BottomNavigationView
    private lateinit var dbHelper: SongDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MusicApp", MODE_PRIVATE)

        // SongDatabaseHelper 인스턴스 초기화
        dbHelper = SongDatabaseHelper(this)

        // DB에 더미 데이터 삽입
        lifecycleScope.launch {
            insertDummySongs()
        }

        // HomeFragment가 기본 프래그먼트로 표시되도록 설정
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, HomeFragment())
                .commit()
        }

        // 상태 표시줄 색상 설정
        window.statusBarColor = resources.getColor(android.R.color.white, theme)

        bottomNavi = binding.bottomNavi

        // BottomNavigationView 아이템 선택 리스너 설정
        bottomNavi.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main, HomeFragment())
                        .commit()
                    true
                }
                R.id.action_audio -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main, AudioFrag())
                        .commit()
                    true
                }
                R.id.action_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main, SearchFrag())
                        .commit()
                    true
                }
                R.id.action_mymusic -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main, MyMusicFragment())
                        .commit()
                    true
                }
                R.id.action_menu -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main, MenuFrag())
                        .commit()
                    true
                }
                else -> false
            }
        }

        // mainPlayerCl을 클릭했을 때 SongActivity로 이동
        binding.mainPlayerCl.setOnClickListener {
            // songId를 얻어와서 SongActivity로 전달
            val songId = getSongIdFromPreferences()
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("songId", songId)
            startActivity(intent)
        }
    }

    // DB에 더미 데이터 삽입
    private suspend fun insertDummySongs() {
        val song1 = Song(id = 1, title = "HAPPY", singer = "DAY6(데이식스)", second = 0, playTime = 190, isPlaying = false, isLike = false)
        val song2 = Song(id = 2, title = "I DO ME", singer = "KiiiKiii (키키)", second = 0, playTime = 191, isPlaying = false, isLike = false)
        val song3 = Song(id=3, title="Drowning", singer = "WOODZ", second = 0, playTime = 245, isPlaying = false, isLike = false)

        // 더미 데이터 삽입 (DB 작업)
        dbHelper.insertSong(song1)
        dbHelper.insertSong(song2)
        dbHelper.insertSong((song3))
    }

    // SharedPreferences에 songId 저장
    private fun saveSongId(songId: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("songId", songId)
        editor.apply()
    }

    // SharedPreferences에서 songId 가져오기
    private fun getSongIdFromPreferences(): Int {
        return sharedPreferences.getInt("songId", 0)  // 기본값으로 0 반환
    }
}
