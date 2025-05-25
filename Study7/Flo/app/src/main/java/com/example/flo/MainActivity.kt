package com.example.flo

import android.content.Intent
import android.os.*
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlinx.coroutines.*
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

    private fun isLoggedIn(): Boolean {
        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        val jwt = prefs.getInt("jwt", -1)
        return jwt != -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isLoggedIn()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDummyAlbums()
        initDummySongs()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()
        }

        initBottomNavigation()
        initMiniPlayerButtons()

        binding.mainListIv.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("songId", song.id) // songId 넘겨주는 것 유지
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

    fun setPlayerStatus(isPlayingNow: Boolean) {
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
        prefs.edit().putInt("songId", song.id).apply()
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

    fun setMiniPlayer(song: Song) {
        this.song = song
        currentSecond = song.second
        totalSecond = song.playTime

        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainMiniplayerProgressSb.progress = (currentSecond * 100000) / totalSecond

        setPlayerStatus(true)

        // songId 저장
        val prefs = getSharedPreferences("song", MODE_PRIVATE)
        prefs.edit().putInt("songId", song.id).apply()
    }

    override fun onStart() {
        super.onStart()
        val prefs = getSharedPreferences("song", MODE_PRIVATE)
        val songId = prefs.getInt("songId", -1)

        if (songId == -1) {
            song = Song(
                id = 0,
                title = "Perfect Day",
                singer = "소란 (SORAN)",
                albumResId = R.drawable.img_album_exp2,
                lyrics1 = "가사 1",
                lyrics2 = "가사 2",
                second = 0,
                playTime = 60,
                isPlaying = false,
                isLike = false,
                albumIdx = 1
            )

            setMiniPlayer(song)
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            val db = SongDatabase.getInstance(this@MainActivity)!!
            val songFromDb = withContext(Dispatchers.IO) {
                db.songDao().getSongById(songId)
            }

            song = songFromDb ?: Song(
                id = 0,
                title = "Perfect Day",
                singer = "소란 (SORAN)",
                albumResId = R.drawable.img_album_exp2,
                lyrics1 = "가사 1",
                lyrics2 = "가사 2",
                second = 0,
                playTime = 60,
                isPlaying = false,
                isLike = false,
                albumIdx = 1
            )

            currentSecond = song.second
            totalSecond = song.playTime
            isPlaying = false
            song = song.copy(isPlaying = false)

            setMiniPlayer(song)
            setPlayerStatus(false)
        }
    }

    override fun onStop() {
        super.onStop()
        stopTimer()
    }

    private fun getSongs(): List<Song> {
        return listOf(
            Song(1, "라일락", "아이유", R.drawable.img_album_exp2, "가사1", "가사2", 0, 60, albumIdx = 2),
            Song(2, "Coin", "아이유", R.drawable.img_album_exp2, "가사1", "가사2", 0, 60, albumIdx = 2),
            Song(3, "Flu", "아이유", R.drawable.img_album_exp2, "가사1", "가사2", 0, 60, albumIdx = 2)
        )
    }

    private fun initDummySongs() {
        val db = SongDatabase.getInstance(this)!!
        CoroutineScope(Dispatchers.IO).launch {
            if (db.songDao().getSongs().isEmpty()) {
                db.songDao().insertAll(getSongs())
            }
        }
    }

    private fun initDummyAlbums() {
        val db = SongDatabase.getInstance(this)!!
        val albums = listOf(
            Album(1, "IU 5집", "아이유", R.drawable.img_album_exp2, info = "2021.05.25 | 댄스 팝"),
            Album(2, "Next Level", "에스파", R.drawable.img_album_exp, info = "2021.05.25 | 댄스 팝"),
            Album(3, "Butter", "BTS", R.drawable.img_album_exp, info = "2021.05.25 | 댄스 팝"),
            Album(4, "Weekend", "태연", R.drawable.img_album_exp2, info = "2021.05.25 | 댄스 팝"),
            Album(5, "Bloom", "TheBoyz", R.drawable.img_album_exp, info = "2021.05.25 | 댄스 팝")
        )

        CoroutineScope(Dispatchers.IO).launch {
            db.albumDao().insertAll(albums)
        }
    }
}
