package com.example.realflocoding

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.*
import me.relex.circleindicator.CircleIndicator3
import java.util.*

class HomeFragment : Fragment() {
    private lateinit var topViewPager: ViewPager2
    private lateinit var bottomViewPager: ViewPager2
    private lateinit var indicator: CircleIndicator3
    private lateinit var recyclerView: RecyclerView
    private lateinit var albumstart: ImageView
    private lateinit var albumImage5: ImageView
    private val handler = Handler(Looper.getMainLooper())
    private var timer: Timer? = null
    private val slideInterval: Long = 3000

    // 타이머 관련 변수
    private var isRunning = false
    private var time = 0L
    private var job: Job? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home1, container, false)

        topViewPager = view.findViewById(R.id.viewpager)
        indicator = view.findViewById(R.id.indicator)
        albumstart = view.findViewById(R.id.albumstart)
        albumImage5 = view.findViewById(R.id.imageView5)

        val viewPagerAdapter = ViewPagerAdapter(this)
        topViewPager.adapter = viewPagerAdapter
        indicator.setViewPager(topViewPager)

        // 타이머 초기화
        loadTimerData()

        albumstart.setOnClickListener {
//            val title = "Song Title" // 앨범 제목
//            val artist = "Artist Name" // 아티스트 이름
//
//            saveTimerData(0L, title, artist, true) // 초기 값 저장
//            startTimer() // 타이머 시작
//            navigateToMainActivity(title, artist)
            CoroutineScope(Dispatchers.IO).launch {
                val db = Room.databaseBuilder(
                    requireContext(),
                    AppDatabase::class.java,
                    "MusicDatabase"
                ).build()

                val albumDao = db.albumDao()
                val album = Album(title = "New Album", singer = "Artist Name", covering = 1)
                albumDao.insertAlbum(album)

                val albums = albumDao.getAllAlbums()
                Log.d("HomeFragment", "Albums: $albums")
            }


        }
        albumImage5.setOnClickListener {
            // ExplainFragment1으로 화면 전환
            replaceFragment(ExplainFragment1())
        }

        startAutoSlide()

        return view
    }

    private fun startAutoSlide() {
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                handler.post {
                    val nextItem = (topViewPager.currentItem + 1) % topViewPager.adapter!!.itemCount
                    topViewPager.setCurrentItem(nextItem, true)
                }
            }
        }, slideInterval, slideInterval)
    }

    private fun startTimer() {
        isRunning = true
        job = CoroutineScope(Dispatchers.Main).launch {
            while (isRunning) {
                delay(1000)
                time += 1
                saveTimerData(time, "Song Title", "Artist Name", isRunning)
                Log.d("HomeFragment", "Timer running: $time seconds")
            }
        }
    }

    private fun pauseTimer() {
        isRunning = false
        job?.cancel()
        saveTimerData(time, "Song Title", "Artist Name", isRunning)
        Log.d("HomeFragment", "Timer paused at: $time seconds")
    }

    private fun navigateToMainActivity(title: String, artist: String) {
        val intent = Intent(activity, MainActivity::class.java)
        intent.putExtra("SONG_TITLE", title)
        intent.putExtra("ARTIST_NAME", artist)
        intent.putExtra("TIME", time)
        startActivity(intent)
    }

    private fun loadTimerData() {
        val sharedPreferences = requireActivity().getSharedPreferences("MusicData", Context.MODE_PRIVATE)
        time = sharedPreferences.getLong("TIME", 0L)
        isRunning = sharedPreferences.getBoolean("IS_RUNNING", false)

        if (isRunning) {
            startTimer() // 타이머가 실행 중인 경우 재시작
        }
    }

    private fun saveTimerData(time: Long, title: String, artist: String, isRunning: Boolean) {
        val sharedPreferences = requireActivity().getSharedPreferences("MusicData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("TIME", time)
        editor.putString("SONG_TITLE", title)
        editor.putString("ARTIST_NAME", artist)
        editor.putBoolean("IS_RUNNING", isRunning)
        editor.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel() // 타이머가 초기화되어 있을 경우에만 중지
        pauseTimer() // 화면을 떠날 때 타이머 일시 정지
    }

    private fun getMusicList(): List<MusicItem> {
        return listOf(
            MusicItem(R.drawable.album_image1, "walking without you", "Artist 1"),
            MusicItem(R.drawable.album_image2, "sunny drop", "Artist 2"),
            MusicItem(R.drawable.album_image3, "evening promise", "Artist 3"),
            MusicItem(R.drawable.album_image4, "Aitoka Koitoka", "Artist 4"),
            MusicItem(R.drawable.album_image5, "pride", "Artist 5")
        )
    }


    // Fragment 교체 메서드
    private fun replaceFragment(fragment: Fragment) {
        activity?.let {
            if (it is MainActivity) {
                val transaction = it.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
                Log.d("HomeFragment", "Fragment replaced with ${fragment::class.java.simpleName}")
            } else {
                Log.d("HomeFragment", "Activity is not MainActivity")
            }
        } ?: Log.d("HomeFragment", "Activity is null")
    }

    companion object {
        private fun replaceFragment(homeFragment: HomeFragment, fragment: Fragment) {
            homeFragment.activity?.let {
                if (it is MainActivity) {
                    val transaction = it.supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragment_container, fragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
            }
        }
    }
}
