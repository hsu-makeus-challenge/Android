package com.example.floclone

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.floclone.Fragment.HomeFragment
import com.example.floclone.data.Song
import com.example.floclone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var song : Song

    // 1. Activity Result Launcher 등록
    private val songActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val title = result.data?.getStringExtra("title")
            val singer = result.data?.getStringExtra("singer")
            val second = result.data?.getIntExtra("second",0)
            val playTime = result.data?.getIntExtra("playTime",60)
            val isPlaying = result.data?.getBooleanExtra("isPlaying",false)
            val songData = Song(title.toString(),singer.toString(),second?:0,playTime?:0,isPlaying?:false)
            title?.let {
                Toast.makeText(this, "선택한 앨범: $it", Toast.LENGTH_SHORT).show()
            }
            setSongData(songData)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()

        song = Song(binding.mainMiniplayerTitleTv.text.toString(),binding.mainMiniplayerSingerTv.text.toString(),0,60,false)


        binding.mainPlayerCl.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title",song.title)
            intent.putExtra("singer",song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)

            songActivityLauncher.launch(intent)
        }
    }

    private fun setSongData(songData: Song){
        song.title = songData.title
        song.singer = songData.singer
        song.second = songData.second
        song.playTime = songData.playTime
        song.isPlaying = songData.isPlaying
    }

    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fl, HomeFragment())
            .commitAllowingStateLoss()
        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_fragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fl, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

//                R.id.look_fragment -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.main_fl, LookFragment())
//                        .commitAllowingStateLoss()
//                    return@setOnItemSelectedListener true
//                }
//
//                R.id.search_fragment -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.main_fl, SearchFragment())
//                        .commitAllowingStateLoss()
//                    return@setOnItemSelectedListener true
//                }
//
//                R.id.storage_fragment -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.main_fl, LockerFragment())
//                        .commitAllowingStateLoss()
//                    return@setOnItemSelectedListener true
//                }
            }
            false
        }
    }
}