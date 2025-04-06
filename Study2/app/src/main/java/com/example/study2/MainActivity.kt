package com.example.study2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.study2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private var lastPosition = 0 // 이전 위치 저장

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupViewPager()
        setupBottomNavigationView()

        // 앱 초기 실행 시 홈화면으로 설정
        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.fragment_home
            binding.viewPager.currentItem = 0
        }
    }

    private fun setupViewPager() {
        val fragmentList = arrayListOf<Fragment>(
            HomeFragment(),
            ShortsFragment(),
            UploadFragment(),
            SubscriptionsFragment(),
            LibraryFragment()
        )

        viewPagerAdapter = ViewPagerAdapter(this, fragmentList)

        binding.viewPager.apply {
            adapter = viewPagerAdapter

            // 페이지 변경 시 바텀네비게이션 아이템도 변경
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    // 바텀 네비게이션 아이템 선택
                    val menuItem = binding.bottomNavigationView.menu.getItem(position)
                    if (!menuItem.isChecked) {
                        menuItem.isChecked = true
                    }

                    lastPosition = position
                }
            })
        }
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            val newPosition = when (item.itemId) {
                R.id.fragment_home -> 0
                R.id.fragment_shorts -> 1
                R.id.fragment_upload -> 2
                R.id.fragment_subscriptions -> 3
                R.id.fragment_library -> 4
                else -> 0
            }

            if (lastPosition != newPosition) {
                // 페이지 방향에 따라 다른 애니메이션 적용
                binding.viewPager.setCurrentItem(newPosition, true)

                val transaction = supportFragmentManager.beginTransaction()

                if (newPosition > lastPosition) {
                    // 오른쪽으로 이동
                    transaction.setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left
                    )
                } else {
                    // 왼쪽으로 이동
                    transaction.setCustomAnimations(
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                    )
                }

            }
            true
        }
    }
}