package com.example.mission3

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.mission3.databinding.ActivitySongBinding

class
SongActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongBinding
    private lateinit var song: Song
    private var countDownTimer: CountDownTimer? = null
    private var isPlaying = false
    private var currentTime = 0 // 현재 재생 시간
    private lateinit var sharedPreferences: SharedPreferences
    private var isFavorite = false
    private var songList: List<Song> = listOf() // 곡 리스트
    private var currentSongIndex: Int = 0 // 현재 곡 인덱스

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyMusicApp", MODE_PRIVATE)

        initSongList()
        initSong()
        setPlayer(song)

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

    private val lyricsList = listOf(
        "그런 날이 있을까요? \n마냥 좋은 그런 날이요", // HAPPY
        "I could go somewhere \nMaybe anywhere", // I DO ME
        "미치도록 사랑했던 \n지겹도록 다투었던" // Drowning
    )

    private fun setLyrics(index: Int) {
        if (index in lyricsList.indices) {
            binding.songAlbumTextTv.text = lyricsList[index]
        }
    }

    private fun initSongList() {
        // 곡 리스트를 불러오기 (예: DB에서 불러오기)
        songList = listOf(
            Song("HAPPY", "DAY6(데이식스)", 0, 190, false, imageRes = R.drawable.happy),
            Song("I DO ME", "KiiiKiii (키키)", 0, 191, false, imageRes = R.drawable.idome),
            Song("Drowning", "WOODZ", 0, 245, false, imageRes = R.drawable.drowning)
        )

        // SharedPreferences에서 현재 곡 인덱스 불러오기
        currentSongIndex = sharedPreferences.getInt("current_song_index", 0)
    }

    private fun initSong() {
        // 곡 정보를 리스트에서 가져오기
        song = songList[currentSongIndex]
    }

    private fun setPlayer(song: Song) {
        binding.songTitleTv.text = song.title
        binding.songSingerTv.text = song.singer
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)

        // 곡 이미지 설정 (새로 추가)
        binding.songAlbumIv.setImageResource(song.imageRes)
        
        binding.songProgressbarView.max = song.playTime * 1000
        binding.songProgressbarView.progress = song.second * 1000

        setPlayerStatus(song.isPlaying)

        // 가사 설정 추가
        setLyrics(currentSongIndex)
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
        countDownTimer = object : CountDownTimer(song.playTime * 1000L, 1000) {
            private var elapsedSeconds = song.second

            override fun onTick(millisUntilFinished: Long) {
                elapsedSeconds++
                binding.songProgressbarView.progress = (elapsedSeconds * 1000)

                binding.songStartTimeTv.text = String.format("%02d:%02d", elapsedSeconds / 60, elapsedSeconds % 60)
            }

            override fun onFinish() {
                binding.songProgressbarView.progress = binding.songProgressbarView.max
                pauseSong() // 재생 완료 후 일시정지 상태로 변경
            }
        }.start()
    }

    private fun playSong() {
        isPlaying = true
        setPlayerStatus(isPlaying)
    }

    private fun pauseSong() {
        isPlaying = false
        countDownTimer?.cancel()
        setPlayerStatus(isPlaying)
    }

    // 즐겨찾기 버튼 상태 업데이트
    private fun updateFavoriteButton() {
        if (isFavorite) {
            binding.favoriteButton.setImageResource(R.drawable.baseline_favorite_24) // 하트 채운 아이콘
        } else {
            binding.favoriteButton.setImageResource(R.drawable.baseline_favorite_border_24) // 하트 비어있는 아이콘
        }
    }

    // 즐겨찾기 상태 저장
    private fun saveFavoriteStatus() {
        // 곡 제목과 ID를 결합하여 고유 키 생성
        val key = "${song.title}-${song.singer}" // 예시로 제목과 가수명을 결합
        sharedPreferences.edit {
            putBoolean(key, isFavorite) // 고유한 키로 즐겨찾기 상태 저장
        }
    }

    // 즐겨찾기 상태 로드
    private fun loadFavoriteStatus() {
        // 곡 제목과 ID를 결합하여 고유 키 생성
        val key = "${song.title}-${song.singer}" // 예시로 제목과 가수명을 결합
        isFavorite = sharedPreferences.getBoolean(key, false)
    }


    private fun goToPreviousSong() {
        if (currentSongIndex > 0) {
            currentSongIndex--
            song = songList[currentSongIndex]
            setPlayer(song)
            stopSong() // 곡 전환 시 이전 곡 멈추기
            playSong() // 새로운 곡 시작

            // SharedPreferences에 현재 곡 인덱스 저장
            sharedPreferences.edit {
                putInt("current_song_index", currentSongIndex)
            }
        }
    }

    private fun goToNextSong() {
        if (currentSongIndex < songList.size - 1) {
            currentSongIndex++
            song = songList[currentSongIndex]
            setPlayer(song)
            stopSong() // 곡 전환 시 이전 곡 멈추기
            playSong() // 새로운 곡 시작

            // SharedPreferences에 현재 곡 인덱스 저장
            sharedPreferences.edit {
                putInt("current_song_index", currentSongIndex)
            }
        }
    }

    private fun stopSong() {
        // 곡 멈추기 (임시로 노래 재생 없이 처리)
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

