package com.example.mission3

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mission3.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var bottomNavi: BottomNavigationView
    private lateinit var dbHelper: SongDatabaseHelper
    private lateinit var songViewModel: SongViewModel

    private val songList = listOf(
        Song(id = 1, title = "HAPPY", singer = "DAY6(데이식스)", second = 0, playTime = 190, isPlaying = false, isLike = false, imageRes = R.drawable.happy),
        Song(id = 2, title = "I DO ME", singer = "KiiiKiii (키키)", second = 0, playTime = 191, isPlaying = false, isLike = false, imageRes = R.drawable.idome),
        Song(id = 3, title = "Drowning", singer = "WOODZ", second = 0, playTime = 245, isPlaying = false, isLike = false, imageRes = R.drawable.drowning)
    )
    private var currentSongIndex = 0
    private var timer: Timer? = null
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        songViewModel = ViewModelProvider(this)[SongViewModel::class.java]

        sharedPreferences = getSharedPreferences("MusicApp", MODE_PRIVATE)
        dbHelper = SongDatabaseHelper(this)

        lifecycleScope.launch {
            insertDummySongs()
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, HomeFragment())
                .commit()
        }

        window.statusBarColor = resources.getColor(android.R.color.white, theme)

        bottomNavi = binding.bottomNavi
        bottomNavi.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main, HomeFragment()).commit()
                    true
                }
                R.id.action_audio -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main, AudioFrag()).commit()
                    true
                }
                R.id.action_search -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main, SearchFrag()).commit()
                    true
                }
                R.id.action_mymusic -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main, MyMusicFragment()).commit()
                    true
                }
                R.id.action_menu -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main, MenuFrag()).commit()
                    true
                }
                else -> false
            }
        }

        binding.mainPlayerCl.setOnClickListener {
            val song = songList[currentSongIndex]
            val currentTime = binding.miniPlayerSeekbar.progress

            val intent = Intent(this, SongActivity::class.java).apply {
                putExtra("current_position", binding.miniPlayerSeekbar.progress)
                putExtra("songTitle", song.title)
                putExtra("songSinger", song.singer)
                putExtra("songProgress", currentTime)
                putExtra("songDuration", song.playTime)
                putExtra("songImageRes", song.imageRes)
            }
            startActivity(intent)
        }

        binding.prevBtn.setOnClickListener {
            currentSongIndex = if (currentSongIndex - 1 < 0) songList.size - 1 else currentSongIndex - 1
            updateMiniPlayer(songList[currentSongIndex])
        }

        binding.nextBtn.setOnClickListener {
            currentSongIndex = (currentSongIndex + 1) % songList.size
            updateMiniPlayer(songList[currentSongIndex])
        }

        binding.playPauseBtn.setOnClickListener {
            isPlaying = !isPlaying
            if (isPlaying) {
                binding.playPauseBtn.setImageResource(R.drawable.baseline_pause_24)
                startSeekBar(songList[currentSongIndex])
            } else {
                binding.playPauseBtn.setImageResource(R.drawable.baseline_play_24)
                timer?.cancel()
            }
        }

        songViewModel.currentSong.observe(this) { song ->
            binding.miniPlayerSongTitleTv.text = song.title
            binding.miniPlayerSingerTv.text = song.singer
            if (song.isPlaying) {
                binding.playPauseBtn.setImageResource(R.drawable.baseline_pause_24)
            } else {
                binding.playPauseBtn.setImageResource(R.drawable.baseline_play_24)
            }
        }

        updateMiniPlayer(songList[currentSongIndex])
    }

    private fun updateMiniPlayer(song: Song) {
        binding.miniPlayerSongTitleTv.text = song.title
        binding.miniPlayerSingerTv.text = song.singer
        binding.miniPlayerSeekbar.max = song.playTime
        binding.miniPlayerSeekbar.progress = song.second
        saveSongId(song.id)

        if (isPlaying) {
            binding.playPauseBtn.setImageResource(R.drawable.baseline_pause_24)
            startSeekBar(song)
        } else {
            binding.playPauseBtn.setImageResource(R.drawable.baseline_play_24)
            timer?.cancel()
        }
    }

    private fun startSeekBar(song: Song) {
        timer?.cancel()
        var progress = binding.miniPlayerSeekbar.progress
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if (progress < song.playTime) {
                        progress++
                        binding.miniPlayerSeekbar.progress = progress
                    } else {
                        timer?.cancel()
                        isPlaying = false
                        binding.playPauseBtn.setImageResource(R.drawable.baseline_play_24)
                    }
                }
            }
        }, 1000, 1000)
    }

    private suspend fun insertDummySongs() {
        for (song in songList) {
            dbHelper.insertSong(song)
        }
    }

    private fun saveSongId(songId: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("songId", songId)
        editor.putInt("songTime", binding.miniPlayerSeekbar.progress)
        editor.apply()
    }

    private fun getSongIdFromPreferences(): Int {
        return sharedPreferences.getInt("songId", 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}
