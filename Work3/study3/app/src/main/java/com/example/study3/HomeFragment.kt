package com.example.study3


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator3
import java.util.*

class HomeFragment : Fragment() {
    private lateinit var topViewPager: ViewPager2
    private lateinit var bottomViewPager: ViewPager2
    private lateinit var indicator: CircleIndicator3
    private lateinit var recyclerView: RecyclerView
    private val handler = Handler(Looper.getMainLooper())
    private var timer: Timer? = null
    private val slideInterval: Long = 3000


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)



        topViewPager = view.findViewById(R.id.viewpager)
        indicator = view.findViewById(R.id.indicator)
        val viewPagerAdapter = ViewPagerAdapter(this)
        topViewPager.adapter = viewPagerAdapter
        indicator.setViewPager(topViewPager)


        bottomViewPager = view.findViewById(R.id.viewpager_fragment)
        val viewPagerAdapter2 = ViewPagerAdapter2(requireActivity()) { position ->
            when (position) {
                0 -> replaceFragment(album1fragment())
                1 -> replaceFragment(album2fragment())
                2 -> replaceFragment(album3fragment())
                3 -> replaceFragment(album4fragment())
            }
        }
        bottomViewPager.adapter = viewPagerAdapter2

        // RecyclerView 설정
        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val musicAdapter = MusicAdapter(getMusicList())
        recyclerView.adapter = musicAdapter

        // RecyclerView에 외곽선(구분선) 추가
        val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)


        // 자동 슬라이드 시작
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

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel() // 타이머가 초기화되어 있을 경우에만 중지
    }

    private fun getMusicList(): List<MusicItem> {
        return listOf(
            MusicItem(R.drawable.album_image1, "walking without you"),
            MusicItem(R.drawable.album_image2, "sunny drop"),
            MusicItem(R.drawable.album_image3, "evening promise"),
            MusicItem(R.drawable.album_image4, "Aitoka Koitoka"),
            MusicItem(R.drawable.album_image5, "pride")
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

