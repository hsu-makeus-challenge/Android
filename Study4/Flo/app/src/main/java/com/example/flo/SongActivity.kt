package com.example.flo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.flo.databinding.ActivitySongBinding
import java.util.Timer
import java.util.TimerTask

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding
    private var nowPos = 0

    private var isRepeatOn = false
    private var isRandomOn = false
    private var isLiked = false

    private var timer: Timer? = null
    private var currentSecond = 0
    private val totalSecond = 60

    private var isPlaying = false

    private val songList = arrayListOf(
        Song("Perfect Day", "소란 (SORAN)", R.drawable.img_album_exp2,
            "오늘은 오늘만은 절대로 아니길 바랬는데", "하늘엔 비 우산없이 이 와중에 버스 놓침"),
        Song("Pink!", "권진아", R.drawable.img_album_exp,
            "자꾸 생각이나", "마음이 소란스러워"),
        Song("디데이", "정승환", R.drawable.img_album_exp3,
            "세상이 조금 가벼워진 기분이야", "이러다가 정말 날아가겠어")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val singer = intent.getStringExtra("singer")
        nowPos = songList.indexOfFirst { it.title == title && it.singer == singer }
        if (nowPos == -1) nowPos = 0

        initSeekBar()
        setSong(nowPos)

        binding.songDownIb.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.none, R.anim.slide_down)
        }

        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(true)
        }

        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }

        binding.songPreviousIv.setOnClickListener {
            if (nowPos > 0) {
                nowPos--
                setSong(nowPos)
            }
        }

        binding.songNextIv.setOnClickListener {
            if (nowPos < songList.size - 1) {
                nowPos++
                setSong(nowPos)
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
            isLiked = !isLiked
            val likeImage = if (isLiked) R.drawable.ic_like_on else R.drawable.ic_like_off
            binding.songLikeIv.setImageResource(likeImage)
        }

        binding.songUnlikeIv.setOnClickListener {
            if (nowPos < songList.size - 1) {
                nowPos++
                setSong(nowPos)
            }
        }
    }

    private fun initSeekBar() {
        binding.songSeekbar.max = totalSecond
        binding.songEndTimeTv.text = "01:00"
        binding.songStartTimeTv.text = "00:00"
    }

    private fun setSong(position: Int) {
        stopTimer()
        currentSecond = 0
        isPlaying = false

        val song = songList[position]
        binding.songMusicTitleTv.text = song.title
        binding.songSingerNameTv.text = song.singer
        binding.songAlbumIv.setImageResource(song.albumResId)
        binding.songMusicLyrics01Tv.text = song.lyrics1
        binding.songMusicLyrics02Tv.text = song.lyrics2

        initSeekBar()
        binding.songSeekbar.progress = currentSecond

        setPlayerStatus(false)
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
        val handler = Handler(Looper.getMainLooper())
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                if (currentSecond >= totalSecond) {
                    stopTimer()
                    return
                }

                currentSecond++
                handler.post {
                    binding.songSeekbar.progress = currentSecond
                    binding.songStartTimeTv.text = String.format("00:%02d", currentSecond)
                }
            }
        }, 1000, 1000)
    }

    private fun stopTimer() {
        timer?.cancel()
        timer = null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
    }
}