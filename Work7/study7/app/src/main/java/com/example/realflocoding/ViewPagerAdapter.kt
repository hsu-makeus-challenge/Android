package com.example.realflocoding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.study3.SlideFragment1
import com.example.study3.SlideFragment2
import com.example.study3.SlideFragment3
import com.example.study3.SlideFragment4

class ViewPagerAdapter(activity: HomeFragment) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 5 // 페이지 수
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SlideFragment() // 별도의 프래그먼트 생성
            1 -> SlideFragment1()
            2 -> SlideFragment2()
            3 -> SlideFragment3()
            else -> SlideFragment4()
        }
    }
}
