package com.example.mission3

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import com.example.mission3.databinding.ActivitySongBinding
import kotlinx.coroutines.launch

class SongActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongBinding
    private lateinit var song: Song
    private var isPlaying = false
    private var currentTime = 0 // 현재 재생 시간 (초 단위)
    private lateinit var sharedPreferences: SharedPreferences
    private var isFavorite = false
    private var songList: List<Song> = listOf() // 곡 리스트
    private var currentSongIndex: Int = 0 // 현재 곡 인덱스

    private lateinit var db: AppDatabase
    private lateinit var likedSongDao: LikedSongDao


    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)
        likedSongDao = db.likedSongDao()


        sharedPreferences = getSharedPreferences("MyMusicApp", MODE_PRIVATE)

        // Intent에서 받은 데이터를 기반으로 초기화
        val songTitle = intent.getStringExtra("songTitle")
        val songSinger = intent.getStringExtra("songSinger")
        currentTime = intent.getIntExtra("songProgress", 0)
        val songDuration = intent.getIntExtra("songDuration", 0)
        val key = "$songTitle-$songSinger"
        val imageResFromPrefs = sharedPreferences.getInt("${key}_imageRes", -1)
        val imageRes = if (imageResFromPrefs != -1) imageResFromPrefs else {
            intent.getIntExtra("songImageRes", R.drawable.happy)
        }

        // Song 객체 초기화
        song = Song(
            title = songTitle ?: "",
            singer = songSinger ?: "",
            second = currentTime,
            playTime = songDuration,
            isPlaying = false,
            isLike = false,
            id = 0,
            imageRes = imageRes
        )


        songList = listOf(
            Song("HAPPY", "DAY6(데이식스)", 0, 190, false, imageRes = R.drawable.happy),
            Song("I DO ME", "KiiiKiii (키키)", 0, 191, false, imageRes = R.drawable.idome),
            Song("Drowning", "WOODZ", 0, 245, false, imageRes = R.drawable.drowning)
        )
        currentSongIndex = songList.indexOf(song) // song에 맞는 인덱스를 찾습니다.

        setPlayer(song)  // 플레이어 설정

        // 즐겨찾기 상태 로드
        loadFavoriteStatus()
        updateFavoriteButton()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.parseColor("#FFFFFF")
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.songSettingIb.setOnClickListener {
            finish()
        }

        binding.playPauseButton.setOnClickListener {
            if (isPlaying) {
                pauseSong()
            } else {
                playSong()
            }
        }

        // 즐겨찾기 버튼 클릭 리스너
        binding.favoriteButton.setOnClickListener {
            isFavorite = !isFavorite
            updateFavoriteButton()

            lifecycleScope.launch {
                val likedSong = LikedSong(
                    title = song.title,
                    singer = song.singer,
                    imageRes = song.imageRes,
                    isLike = isFavorite
                )

                if (isFavorite) {
                    likedSongDao.insert(likedSong)
                    runOnUiThread {
                        Toast.makeText(this@SongActivity, "좋아요 저장 완료", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    likedSongDao.delete(likedSong)
                    runOnUiThread {
                        Toast.makeText(this@SongActivity, "좋아요 취소", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            // SharedPreferences도 함께 저장
            saveFavoriteStatus()
        }

        // 이전 곡 버튼 클릭 리스너
        binding.skipPrevious.setOnClickListener {
            goToPreviousSong()
        }

        // 다음 곡 버튼 클릭 리스너
        binding.songNextIv.setOnClickListener {
            goToNextSong()
        }
    }

    private fun setPlayer(song: Song) {
        binding.songTitleTv.text = song.title
        binding.songSingerTv.text = song.singer
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)

        binding.songAlbumIv.setImageResource(song.imageRes)

        binding.songProgressbarView.max = song.playTime * 1000
        binding.songProgressbarView.progress = song.second * 1000

        setPlayerStatus(song.isPlaying)
    }

    private fun setPlayerStatus(isPlaying: Boolean) {
        if (isPlaying) {
            binding.playPauseButton.setImageResource(R.drawable.baseline_pause_24)
            binding.songProgressbarView.progressTintList = ColorStateList.valueOf(Color.parseColor("#81BEF7"))
            startTimer() // 타이머 시작
        } else {
            binding.playPauseButton.setImageResource(R.drawable.baseline_play_24)
            binding.songProgressbarView.progressTintList = ColorStateList.valueOf(Color.GRAY)
        }
    }

    private fun startTimer() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (isPlaying) {
                    currentTime++
                    updateSeekBar() // SeekBar 업데이트
                    updateTimeDisplay() // 시간 텍스트 업데이트
                    if (currentTime < song.playTime) {
                        handler.postDelayed(this, 1000) // 1초 후에 다시 실행
                    }
                }
            }
        }, 1000)
    }

    private fun updateSeekBar() {
        binding.songProgressbarView.progress = currentTime * 1000
    }

    private fun updateTimeDisplay() {
        val minutes = currentTime / 60
        val seconds = currentTime % 60
        binding.songStartTimeTv.text = String.format("%02d:%02d", minutes, seconds)

        val remainingTime = song.playTime - currentTime
        val endMinutes = remainingTime / 60
        val endSeconds = remainingTime % 60
        binding.songEndTimeTv.text = String.format("-%02d:%02d", endMinutes, endSeconds)
    }

    private fun playSong() {
        isPlaying = true
        setPlayerStatus(isPlaying)
    }

    private fun pauseSong() {
        isPlaying = false
        handler.removeCallbacksAndMessages(null) // 타이머 취소
        setPlayerStatus(isPlaying)
    }

    private fun updateFavoriteButton() {
        if (isFavorite) {
            binding.favoriteButton.setImageResource(R.drawable.baseline_favorite_24)
        } else {
            binding.favoriteButton.setImageResource(R.drawable.baseline_favorite_border_24)
        }
    }

    private fun saveFavoriteStatus() {
        val key = "${song.title}-${song.singer}"
        sharedPreferences.edit {
            putBoolean(key, isFavorite)
            putInt("${key}_imageRes", song.imageRes)  // 이미지 리소스 ID 저장
        }
    }

    private fun loadFavoriteStatus() {
        val key = "${song.title}-${song.singer}"
        isFavorite = sharedPreferences.getBoolean(key, false)
    }

    private fun goToPreviousSong() {
        if (currentSongIndex > 0) {
            currentSongIndex--
            song = songList[currentSongIndex]
            setPlayer(song)
            stopSong()
            playSong()

            sharedPreferences.edit {
                putInt("current_song_index", currentSongIndex)
            }
        }
    }

    private fun goToNextSong() {
        if (currentSongIndex < songList.size - 1) {
            currentSongIndex++
            song = songList[currentSongIndex]
            currentTime = 0 // 시간 초기화
            setPlayer(song)
            stopSong()
            playSong()

            sharedPreferences.edit {
                putInt("current_song_index", currentSongIndex)
            }
        }
    }

    private fun stopSong() {
        // 곡 멈추기
        isPlaying = false
        handler.removeCallbacksAndMessages(null)
    }

    override fun onPause() {
        super.onPause()
        // 타이머는 멈추지 않고 상태 유지
    }

    override fun onResume() {
        super.onResume()
        // 화면이 다시 보여질 때 타이머가 계속 진행되도록 설정
        if (isPlaying) {
            startTimer() // 타이머 다시 시작
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null) // 화면 종료 시 타이머 정지
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}


