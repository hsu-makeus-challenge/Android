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
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var song : Song = Song()
    private var gson : Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()

//        song = Song(binding.mainMiniplayerTitleTv.text.toString(),binding.mainMiniplayerSingerTv.text.toString(),0,60,false)


        binding.mainPlayerCl.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title",song.title)
            intent.putExtra("singer",song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
            startActivity(intent)

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

    private fun setMiniPlayer(song: Song){
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainMiniplayerProgressSb.progress = (song.second*100000)/song.playTime
    }

    override fun onStart(){
        super.onStart()
        val sharedPreferences = getSharedPreferences("song",MODE_PRIVATE)
        val songJson = sharedPreferences.getString("songData",null)

        song = if(songJson == null){
            Song("라일락", "아이유(IU)", 0, 60, false)
        }else{
            gson.fromJson(songJson, Song::class.java)
        }

        setMiniPlayer(song)
    }
}