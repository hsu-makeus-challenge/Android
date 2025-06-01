package com.example.floclone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.floclone.Fragment.HomeFragment
import com.example.floclone.Fragment.StorageFragment
import com.example.floclone.data.Album
import com.example.floclone.data.Song
import com.example.floclone.data.SongDatabase
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

        inputDummySongs()
        inputDummyAlbums()
        initBottomNavigation()

//        song = Song(binding.mainMiniplayerTitleTv.text.toString(),binding.mainMiniplayerSingerTv.text.toString(),0,60,false)


        binding.mainPlayerCl.setOnClickListener {
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId",song.id)
            editor.apply()

            val intent = Intent(this, SongActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setSongData(songData: Song){
        song.title = songData.title
        song.singer = songData.singer
        song.second = songData.second
        song.playTime = songData.playTime
        song.isPlaying = songData.isPlaying
        song.coverImg = songData.coverImg
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
                R.id.storage_fragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fl, StorageFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
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
//        val sharedPreferences = getSharedPreferences("song",MODE_PRIVATE)
//        val songJson = sharedPreferences.getString("songData",null)
//
//        song = if(songJson == null){
//            Song("Drowning", "WOODZ", 0, 60, false, "music_drowning")
//        }else{
//            gson.fromJson(songJson, Song::class.java)
//        }
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        val songDB = SongDatabase.getInstance(this)!!

        song = if(songId == 0){
            songDB.songDao().getSong(1)
        } else{
            songDB.songDao().getSong(songId)
        }

        Log.d("song ID", song.id.toString())
        setMiniPlayer(song)
    }

    private fun inputDummySongs(){
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.songDao().getSongs()

        if(songs.isNotEmpty()) return

        songDB.songDao().insert(
            Song("Drowning", "WOODZ", 0, 240, false, "music_drowning", R.drawable.img_album_drowning,false)
        )

        songDB.songDao().insert(
            Song("사라지나요", "PATEKO", 0, 270, false, "music_pateko", R.drawable.img_album_pateko,false)
        )

        songDB.songDao().insert(
            Song("Thirsty", "aespa", 0, 190, false, "music_thirsty", R.drawable.img_album_thirsty,false)
        )

        songDB.songDao().insert(
            Song("MOON", "아이들", 0, 200, false, "music_moon", R.drawable.img_album_moon,false)
        )

        songDB.songDao().insert(
            Song("Welcome To The Show", "DAY6", 0, 210, false, "music_welcometotheshow", R.drawable.img_album_welcometotheshow,false)
        )

        songDB.songDao().insert(
            Song("GONE", "릴러말즈", 0, 220, false, "music_gone", R.drawable.img_album_gone,false)
        )

        val _songs = songDB.songDao().getSongs()
        Log.d("DB data", _songs.toString())
    }

    private fun inputDummyAlbums(){
        val songDB = SongDatabase.getInstance(this)!!
        val albums = songDB.albumDao().getAlbums()

        if(albums.isNotEmpty()) return

        songDB.albumDao().insert(
            Album(0,"IU 5th Album 'LILAC'", "아이유 (IU)", R.drawable.img_album_exp)
        )

        songDB.albumDao().insert(
            Album(1,"앨범2", "아이유 (IU)", R.drawable.img_album_exp2)
        )

        songDB.albumDao().insert(
            Album(2,"앨범3", "아이유 (IU)", R.drawable.img_album_exp3)
        )

        songDB.albumDao().insert(
            Album(3,"앨범4", "아이유 (IU)", R.drawable.img_album_drowning)
        )

        songDB.albumDao().insert(
            Album(4,"앨범5", "아이유 (IU)", R.drawable.img_album_gone)
        )


        val _albums = songDB.albumDao().getAlbums()
        Log.d("DB data", _albums.toString())
    }
}