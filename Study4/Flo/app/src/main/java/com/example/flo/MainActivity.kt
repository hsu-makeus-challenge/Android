package com.example.flo

import android.content.Intent
import android.os.*
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var song: Song
    private val gson = Gson()
    private val handler = Handler(Looper.getMainLooper())

    private var currentSecond = 0
    private var totalSecond = 60
    private var isPlaying = false
    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()
        }

        initBottomNavigation()
        initMiniPlayerButtons()

        binding.mainListIv.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("imageResId", song.albumResId)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_up, R.anim.none)
        }
    }

    private fun initMiniPlayerButtons() {
        binding.mainMiniplayerBtn.setOnClickListener {
            setPlayerStatus(true)
        }

        binding.mainPauseBtn.setOnClickListener {
            setPlayerStatus(false)
        }
    }

    private fun setPlayerStatus(isPlayingNow: Boolean) {
        isPlaying = isPlayingNow

        if (isPlaying) {
            binding.mainMiniplayerBtn.visibility = View.GONE
            binding.mainPauseBtn.visibility = View.VISIBLE
            startTimer()
        } else {
            binding.mainMiniplayerBtn.visibility = View.VISIBLE
            binding.mainPauseBtn.visibility = View.GONE
            stopTimer()
        }
    }

    private fun startTimer() {
        stopTimer()
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                currentSecond++
                if (currentSecond > totalSecond) {
                    stopTimer()
                } else {
                    updateUIAndState()
                }
            }
        }, 1000, 1000)
    }

    private fun updateUIAndState() {
        handler.post {
            binding.mainMiniplayerProgressSb.progress = (currentSecond * 100000) / totalSecond
            saveSongProgress()
        }
    }

    private fun stopTimer() {
        timer?.cancel()
        timer = null
    }

    private fun saveSongProgress() {
        val updatedSong = song.copy(
            second = currentSecond,
            isPlaying = isPlaying,
            playTime = totalSecond
        )
        val prefs = getSharedPreferences("song", MODE_PRIVATE)
        prefs.edit().putString("songData", gson.toJson(updatedSong)).apply()
    }

    private fun initBottomNavigation() {
        binding.mainBnv.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.homeFragment -> HomeFragment()
                R.id.lookFragment -> LookFragment()
                R.id.searchFragment -> SearchFragment()
                R.id.lockerFragment -> LockerFragment()
                else -> null
            }
            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, it)
                    .commitAllowingStateLoss()
                true
            } ?: false
        }
    }

    private fun setMiniPlayer(song: Song) {
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainMiniplayerProgressSb.progress = (song.second * 100000) / song.playTime
    }

    override fun onStart() {
        super.onStart()
        val prefs = getSharedPreferences("song", MODE_PRIVATE)
        val songJson = prefs.getString("songData", null)

        song = if (songJson == null) {
            Song("Perfect Day", "소란 (SORAN)", R.drawable.img_album_exp2)
        } else {
            gson.fromJson(songJson, Song::class.java)
        }

        currentSecond = song.second
        totalSecond = song.playTime
        isPlaying = song.isPlaying

        setMiniPlayer(song)

        if (isPlaying) {
            setPlayerStatus(true)
        }
    }

    override fun onStop() {
        super.onStop()
        stopTimer()
    }
}
