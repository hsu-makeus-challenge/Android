package com.example.realflocoding

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var seekBar2: SeekBar
    private lateinit var mainImage: ImageView
    private lateinit var textsong: TextView
    private lateinit var textsinger: TextView
    private lateinit var nextSongBtn: ImageView
    private lateinit var prevSongBtn: ImageView
    private lateinit var songImage :ImageView

    private var isRunning = false
    private var time = 0L
    private var job: Job? = null

    private lateinit var database: SQLiteDatabase
    private lateinit var sharedPreferences: SharedPreferences

    private var currentSongId: Int = 1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        mainImage = findViewById(R.id.startmain)
        nextSongBtn = findViewById(R.id.btn_next)
        prevSongBtn = findViewById(R.id.btn_previous)
        textsong = findViewById(R.id.song_title)
        textsinger = findViewById(R.id.song_singer)
        seekBar2 = findViewById(R.id.seekBar2)
        songImage= findViewById(R.id.listimage)

        // Initialize sharedPreferences
        sharedPreferences = getSharedPreferences("MusicData", Context.MODE_PRIVATE)
        currentSongId = sharedPreferences.getInt("songId", 1) // Default song is the first song

        // Initialize SQLite database
        initDatabase()

        // Load song info
        loadSongInfo()

        // Start/Pause music on image click
        mainImage.setOnClickListener {
            if (!isRunning) {
                startTimer()
                mainImage.setImageResource(R.drawable.btn_miniplay_mvpause)
            } else {
                pauseTimer()
                mainImage.setImageResource(R.drawable.btn_miniplayer_play)
            }
        }

        // Next song button click
        nextSongBtn.setOnClickListener {
            loadNextSong()
        }
        songImage.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        // Previous song button click
        prevSongBtn.setOnClickListener {
            loadPreviousSong()
        }

        // Bottom Navigation View setup
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.search -> {
                    loadFragment(SearchFragment())
                    true
                }
                R.id.somelse -> {
                    loadFragment(SomelseFragment())
                    true
                }
                R.id.stack -> {
                    loadFragment(StackFragment())
                    true
                }
                else -> false
            }
        }

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }
    }

    // Initialize SQLite database
    private fun initDatabase() {
        database = openOrCreateDatabase("MusicDB", Context.MODE_PRIVATE, null)
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS Songs (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                artist TEXT NOT NULL
            )
            """
        )

        // Add some songs if not already present
        val dummyData = listOf(
            "Odoriko" to "Vaundy",
            "Kaikai Kitan" to "Eve",
            "Yoru ni Kakeru" to "YOASOBI",
            "KICK BACK" to "Kenshi Yonezu",
            "Lemon" to "Kenshi Yonezu",
            "Pretender" to "Official HIGE DANdism"
        )

        for ((title, artist) in dummyData) {
            val cursor = database.rawQuery("SELECT * FROM Songs WHERE title = ? AND artist = ?", arrayOf(title, artist))
            if (cursor.count == 0) {
                database.execSQL(
                    "INSERT INTO Songs (title, artist) VALUES (?, ?)",
                    arrayOf(title, artist)
                )
            }
            cursor.close()
        }
    }

    // Load the current song info from the database
    private fun loadSongInfo() {
        val cursor = database.rawQuery("SELECT * FROM Songs WHERE id = ?", arrayOf(currentSongId.toString()))
        if (cursor.moveToFirst()) {
            val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
            val artist = cursor.getString(cursor.getColumnIndexOrThrow("artist"))

            textsong.text = title
            textsinger.text = artist
        }
        cursor.close()
    }

    // Save current song ID to SharedPreferences
    private fun saveSongId(songId: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("songId", songId)
        editor.apply()
    }

    // Load next song
    private fun loadNextSong() {
        currentSongId++
        if (currentSongId > getMaxSongId()) {
            currentSongId = 1 // Loop back to the first song
        }
        loadSongInfo()
        saveSongId(currentSongId)
    }

    // Load previous song
    private fun loadPreviousSong() {
        currentSongId--
        if (currentSongId < 1) {
            currentSongId = getMaxSongId() // Loop back to the last song
        }
        loadSongInfo()
        saveSongId(currentSongId)
    }

    // Get the maximum song ID in the database
    private fun getMaxSongId(): Int {
        val cursor = database.rawQuery("SELECT MAX(id) FROM Songs", null)
        var maxId = 1
        if (cursor.moveToFirst()) {
            maxId = cursor.getInt(0)
        }
        cursor.close()
        return maxId
    }

    // Start the timer for music playback
    private fun startTimer() {
        isRunning = true
        job = CoroutineScope(Dispatchers.Main).launch {
            while (isRunning) {
                delay(1000)
                time += 1
                seekBar2.progress = time.toInt()
                saveTimerState()
            }
        }
    }

    // Pause the timer
    private fun pauseTimer() {
        isRunning = false
        job?.cancel()
        saveTimerState()
    }

    // Save the timer state to SharedPreferences
    private fun saveTimerState() {
        val editor = sharedPreferences.edit()
        editor.putLong("TIME", time)
        editor.putBoolean("IS_RUNNING", isRunning)
        editor.apply()
    }

    // Load a fragment into the container
    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        database.close()
    }
}
