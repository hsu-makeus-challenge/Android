package com.example.flo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()

        // 미니플레이어 클릭 시 SongActivity로 이동
        binding.mainPlayerCl.setOnClickListener {
            val song = Song(
                title = binding.mainMiniplayerTitleTv.text.toString(),
                singer = binding.mainMiniplayerSingerTv.text.toString(),
                albumResId = R.drawable.img_album_exp2, // 임의 앨범 이미지 (선택적 수정)
                lyrics1 = "오늘은 오늘만은 절대로 아니길 바랬는데",
                lyrics2 = "하늘엔 비 우산없이 이 와중에 버스 놓침"
            )

            val intent = Intent(this, SongActivity::class.java).apply {
                putExtra("title", song.title)
                putExtra("singer", song.singer)
                putExtra("albumResId", song.albumResId)
                putExtra("lyrics1", song.lyrics1)
                putExtra("lyrics2", song.lyrics2)
            }
            startActivity(intent)
        }
    }

    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    true
                }
                else -> false
            }
        }
    }
}
