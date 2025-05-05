package com.example.mission3

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var songProgressSb: SeekBar

    // 곡 리스트 및 현재 인덱스
    private val songList = listOf(
        Song(id = 1, title = "HAPPY", singer = "DAY6(데이식스)", second = 0, playTime = 190, isPlaying = false, isLike = false, imageRes = R.drawable.happy),
        Song(id = 2, title = "I DO ME", singer = "KiiiKiii (키키)", second = 0, playTime = 191, isPlaying = false, isLike = false, imageRes = R.drawable.idome),
        Song(id = 3, title = "Drowning", singer = "WOODZ", second = 0, playTime = 245, isPlaying = false, isLike = false, imageRes = R.drawable.drowning)
    )
    private var currentSongIndex = 0
    private var timer: Timer? = null
    private var isPlaying = false // ▶️ 재생 상태

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // songProgressSb를 찾고 초기화
        songProgressSb = findViewById(R.id.mini_player_seekbar)

        sharedPreferences = getSharedPreferences("MusicApp", MODE_PRIVATE)
        dbHelper = SongDatabaseHelper(this)

        // DB에 더미 데이터 삽입
        lifecycleScope.launch {
            insertDummySongs()
        }

        // 기본 프래그먼트
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, HomeFragment())
                .commit()
        }

        // 상태 표시줄 색상
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

        // mini-player 클릭 시 SongActivity로 이동
        binding.mainPlayerCl.setOnClickListener {
            val song = songList[currentSongIndex]
            val currentTime = binding.miniPlayerSeekbar.progress  // 현재 시간 (SeekBar의 위치)

            // Intent에 추가 데이터 넣기
            val intent = Intent(this, SongActivity::class.java).apply {
                intent.putExtra("current_position", songProgressSb.progress) // 현재 진행 상태를 전달
                putExtra("songTitle", song.title)  // 곡 제목
                putExtra("songSinger", song.singer)  // 곡 아티스트
                putExtra("songProgress", currentTime)  // SeekBar의 현재 위치 (초 단위)
                putExtra("songDuration", song.playTime)  // 곡 전체 길이 (초 단위)
                putExtra("songImageRes", song.imageRes) // 앨범
            }
            startActivity(intent)
        }


        // 이전 곡 버튼
        binding.prevBtn.setOnClickListener {
            currentSongIndex = if (currentSongIndex - 1 < 0) songList.size - 1 else currentSongIndex - 1
            updateMiniPlayer(songList[currentSongIndex])
        }

        // 다음 곡 버튼
        binding.nextBtn.setOnClickListener {
            currentSongIndex = (currentSongIndex + 1) % songList.size
            updateMiniPlayer(songList[currentSongIndex])
        }

        // Play/Pause 버튼
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

        // 초기 미니플레이어 설정
        updateMiniPlayer(songList[currentSongIndex])
    }

    // 미니플레이어 UI 업데이트
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

    // SeekBar 업데이트 타이머
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

    // DB에 더미 데이터 삽입
    private suspend fun insertDummySongs() {
        for (song in songList) {
            dbHelper.insertSong(song)
        }
    }

    // SharedPreferences 저장/불러오기
    private fun saveSongId(songId: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("songId", songId)
        editor.putInt("songTime", binding.miniPlayerSeekbar.progress) // 현재 시간도 저장
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
