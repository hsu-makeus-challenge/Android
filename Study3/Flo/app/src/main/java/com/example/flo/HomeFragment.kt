package com.example.flo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private lateinit var bannerAdapter: BannerVPAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        bannerAdapter = BannerVPAdapter(getBannerList())
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // 초기 위치 중앙쯤으로 지정
        val startPosition = Int.MAX_VALUE / 2
        binding.homeBannerVp.setCurrentItem(startPosition - (startPosition % getBannerList().size), false)

        startAutoScroll()

        return binding.root
    }

    private fun getBannerList(): List<Int> {
        return listOf(
            R.drawable.img_home_viewpager_exp,
            R.drawable.img_home_viewpager_exp2,
            R.drawable.img_home_viewpager_exp3
        )
    }

    private fun startAutoScroll() {
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                val nextItem = binding.homeBannerVp.currentItem + 1
                binding.homeBannerVp.setCurrentItem(nextItem, true)
                handler.postDelayed(this, 3000)
            }
        }
        handler.postDelayed(runnable, 3000)
    }
}
