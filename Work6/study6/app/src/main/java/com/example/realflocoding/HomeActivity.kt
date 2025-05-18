package com.example.realflocoding

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class Song(val id: Int, val title: String, val artist: String, var isLiked: Boolean)

class HomeActivity : AppCompatActivity() {
    private lateinit var seekBar: SeekBar
    private lateinit var timeTextView: TextView
    private lateinit var playPauseImage: ImageView
    private lateinit var likeunlikeImage: ImageView
    private lateinit var nextImage: ImageView
    private lateinit var prevImage: ImageView
    private lateinit var mainImage: ImageView
    private lateinit var reImage: ImageView
    private lateinit var textsong: TextView
    private lateinit var textsinger: TextView
    private lateinit var backImage : ImageView

    private var time = 0L
    private var isRunning = false
    private var job: Job? = null
    private var nowPos = 0
    private val songs = mutableListOf<Song>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // View 초기화
        playPauseImage = findViewById(R.id.imageView12)
        timeTextView = findViewById(R.id.timeTextView)
        seekBar = findViewById(R.id.seekBar)
        likeunlikeImage = findViewById(R.id.imagelike)
        nextImage = findViewById(R.id.imageView10)
        prevImage = findViewById(R.id.imageView13)
        mainImage = findViewById(R.id.imageView9)
        reImage = findViewById(R.id.recycle)
        textsong = findViewById(R.id.textView14)
        textsinger = findViewById(R.id.textView15)
        backImage = findViewById(R.id.back)
        // DB 데이터 초기화
        initializeSongs()

        // 현재 songId로 nowPos 설정
        val sharedPreferences = getSharedPreferences("MusicData", Context.MODE_PRIVATE)
        val savedSongId = sharedPreferences.getInt("SONG_ID", 0)
        nowPos = songs.indexOfFirst { it.id == savedSongId }.takeIf { it != -1 } ?: 0

        // UI 초기화
        updateUI()

        // SeekBar 설정
        seekBar.max = 180
        seekBar.progress = time.toInt()
        timeTextView.text = formatTime(time)

        // 버튼 클릭 이벤트 처리
        nextImage.setOnClickListener { moveToNextSong() }
        prevImage.setOnClickListener { moveToPreviousSong() }
        likeunlikeImage.setOnClickListener { toggleLike() }
        playPauseImage.setOnClickListener { togglePlayPause() }
        reImage.setOnClickListener { clearTimer() }
        backImage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initializeSongs() {
        songs.apply {
            add(Song(1, "Odoriko", "Vaundy", false))
            add(Song(2, "Fujin", "Vaundy", false))
            add(Song(3, "Tokyo Flash", "Vaundy", false))
            add(Song(4, "Kaiju", "Vaundy", false))
        }
    }

    private fun updateUI() {
        val currentSong = songs[nowPos]
        textsong.text = currentSong.title
        textsinger.text = currentSong.artist
        likeunlikeImage.setImageResource(
            if (currentSong.isLiked) R.drawable.ic_my_like_on else R.drawable.ic_my_like_off
        )
    }

    private fun moveToNextSong() {
        nowPos = (nowPos + 1) % songs.size
        restartMediaPlayer()
        updateUI()
    }

    private fun moveToPreviousSong() {
        nowPos = if (nowPos - 1 < 0) songs.size - 1 else nowPos - 1
        restartMediaPlayer()
        updateUI()
    }

    private fun toggleLike() {
        val currentSong = songs[nowPos]
        currentSong.isLiked = !currentSong.isLiked
        likeunlikeImage.setImageResource(
            if (currentSong.isLiked) R.drawable.ic_my_like_on else R.drawable.ic_my_like_off
        )
        showCustomToast(currentSong.isLiked)
        // DB 업데이트 로직 추가 필요 (예: Room DB 사용 시 업데이트 코드 삽입)
    }
    private fun showCustomToast(isLiked: Boolean) {
        val layoutInflater = LayoutInflater.from(this)
        val customView: View = layoutInflater.inflate(R.layout.toast_custom, null)

        val toastIcon: ImageView = customView.findViewById(R.id.toast_icon)
        val toastMessage: TextView = customView.findViewById(R.id.toast_message)

        if (isLiked) {
            toastIcon.setImageResource(R.drawable.ic_my_like_on)
            toastMessage.text = "좋아요 추가"
        } else {
            toastIcon.setImageResource(R.drawable.ic_my_like_off)
            toastMessage.text = "좋아요 취소"
        }

        val toast = Toast(this)
        toast.view = customView
        toast.duration = Toast.LENGTH_SHORT
        toast.show()
    }

    private fun togglePlayPause() {
        if (!isRunning) {
            startTimer()
            playPauseImage.setImageResource(R.drawable.btn_miniplay_mvpause)
        } else {
            pauseTimer()
            playPauseImage.setImageResource(R.drawable.btn_miniplay_mvplay)
        }
    }

    private fun restartMediaPlayer() {
        pauseTimer() // 기존 재생 중단
        clearTimer() // SeekBar 및 시간 초기화
        startTimer() // 새로운 곡 재생 시작
    }

    // Timer, SharedPreferences 관련 코드는 기존과 동일
    private fun startTimer() {
        isRunning = true
        job = CoroutineScope(Dispatchers.Main).launch {
            while (isRunning) {
                delay(1000)
                time += 1
                seekBar.progress = time.toInt()
                timeTextView.text = formatTime(time)
            }
        }
    }

    private fun pauseTimer() {
        isRunning = false
        job?.cancel()
    }

    private fun clearTimer() {
        pauseTimer()
        time = 0L
        timeTextView.text = formatTime(time)
        seekBar.progress = 0
    }

    private fun formatTime(seconds: Long): String {
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        return String.format("%02d:%02d", minutes, secs)
    }
}
