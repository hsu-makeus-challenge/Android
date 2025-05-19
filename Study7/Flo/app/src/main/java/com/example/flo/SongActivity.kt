package com.example.flo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.util.*

class SongActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongBinding
    private lateinit var song: Song
    private lateinit var songs: MutableList<Song>

    private var currentSecond = 0
    private var isPlaying = false
    private var isRepeatOn = false
    private var isRandomOn = false

    private var nowPos = 0
    private var timer: Timer? = null
    private val totalSecond = 60
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.IO).launch {
            val songId = getSharedPreferences("song", MODE_PRIVATE).getInt("songId", -1)
            val db = SongDatabase.getInstance(this@SongActivity)
            songs = db.songDao().getSongs().toMutableList()
            val loadedSong = db.songDao().getSongById(songId)

            loadedSong?.let { loaded ->
                nowPos = songs.indexOfFirst { it.id == loaded.id }

                withContext(Dispatchers.Main) {
                    song = loaded
                    setSongInfo(song)
                    initSeekBar()
                    setPlayerStatus(song.isPlaying)
                }
            }
        }

        binding.songDownIb.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.none, R.anim.slide_down)
        }

        binding.songMiniplayerIv.setOnClickListener { setPlayerStatus(true) }
        binding.songPauseIv.setOnClickListener { setPlayerStatus(false) }

        binding.songPreviousIv.setOnClickListener {
            if (nowPos > 0) {
                nowPos--
                setSongWithNowPos()
            }
        }

        binding.songNextIv.setOnClickListener {
            if (nowPos < songs.size - 1) {
                nowPos++
                setSongWithNowPos()
            }
        }

        binding.songRepeatIv.setOnClickListener {
            isRepeatOn = !isRepeatOn
            val color = if (isRepeatOn) R.color.select_color else R.color.gray_color
            binding.songRepeatIv.setColorFilter(ContextCompat.getColor(this, color))
        }

        binding.songRandomIv.setOnClickListener {
            isRandomOn = !isRandomOn
            val color = if (isRandomOn) R.color.select_color else R.color.gray_color
            binding.songRandomIv.setColorFilter(ContextCompat.getColor(this, color))
        }

        binding.songLikeIv.setOnClickListener {
            val isLiked = !song.isLike
            val likeImage = if (isLiked) R.drawable.ic_like_on else R.drawable.ic_like_off
            binding.songLikeIv.setImageResource(likeImage)

            val message = if (isLiked) "좋아요에 담겼어요" else "좋아요에서 삭제했어요"
            val iconRes = if (isLiked) R.drawable.ic_like_on else R.drawable.ic_like_off
            showCustomToast(message, iconRes)

            val updatedSong = song.copy(isLike = isLiked)

            CoroutineScope(Dispatchers.IO).launch {
                SongDatabase.getInstance(this@SongActivity).songDao().update(updatedSong)
            }

            song = updatedSong
            songs[nowPos] = updatedSong
        }

        // 좋아요 옆 버튼 클릭 시 다음 곡으로 전환 + 토스트 출력
        binding.songUnlikeIv.setOnClickListener {
            if (nowPos < songs.size - 1) {
                nowPos++
                setSongWithNowPos()
                Toast.makeText(this, "다음 곡으로 넘어갑니다", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "마지막 곡입니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setSongInfo(song: Song) {
        binding.songMusicTitleTv.text = song.title
        binding.songSingerNameTv.text = song.singer
        binding.songAlbumIv.setImageResource(song.albumResId)
        binding.songMusicLyrics01Tv.text = song.lyrics1
        binding.songMusicLyrics02Tv.text = song.lyrics2

        val likeImage = if (song.isLike) R.drawable.ic_like_on else R.drawable.ic_like_off
        binding.songLikeIv.setImageResource(likeImage)
    }

    private fun setSongWithNowPos() {
        stopTimer()
        currentSecond = 0
        song = songs[nowPos]
        setSongInfo(song)
        initSeekBar()
        setPlayerStatus(true)
    }

    private fun initSeekBar() {
        binding.songSeekbar.max = song.playTime
        currentSecond = song.second
        binding.songSeekbar.progress = currentSecond
        updateStartTimeText()
        binding.songEndTimeTv.text = "01:00"
    }

    private fun updateStartTimeText() {
        val minutes = currentSecond / 60
        val seconds = currentSecond % 60
        binding.songStartTimeTv.text = String.format("%02d:%02d", minutes, seconds)
    }

    private fun setPlayerStatus(isPlayingNow: Boolean) {
        isPlaying = isPlayingNow

        if (isPlaying) {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            startTimer()
        } else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            stopTimer()
        }
    }

    private fun startTimer() {
        if (timer != null) return

        val handler = Handler(Looper.getMainLooper())
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                if (currentSecond >= totalSecond) {
                    if (isRepeatOn) {
                        currentSecond = 0
                    } else {
                        stopTimer()
                        return
                    }
                }

                currentSecond++
                handler.post {
                    binding.songSeekbar.progress = currentSecond
                    updateStartTimeText()
                    saveSongProgress()
                }
            }
        }, 1000, 1000)
    }

    @Synchronized
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

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
    }

    private fun showCustomToast(message: String, iconRes: Int) {
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.custom_toast, null)

        layout.findViewById<TextView>(R.id.toast_message_tv).text = message
        layout.findViewById<ImageView>(R.id.toast_icon_iv).setImageResource(iconRes)

        val toast = Toast(this)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }
}
