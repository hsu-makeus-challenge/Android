package com.example.project2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private var currentFragment: Fragment? = null  // 현재 프래그먼트 저장

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // 처음 시작할 때 홈 프래그먼트 보여주기
        val homeFragment = HomeFragment()
        replaceFragment(homeFragment, false)
        currentFragment = homeFragment

        // 홈 버튼을 기본 선택 상태로 설정
        bottomNavigationView.selectedItemId = R.id.nav_home

        bottomNavigationView.setOnItemSelectedListener { item ->
            val selectedFragment = when (item.itemId) {
                R.id.nav_category -> CategoryFragment()
                R.id.nav_shutter -> ShutterFragment()
                R.id.nav_home -> HomeFragment()
                R.id.nav_history -> HistoryFragment()
                R.id.nav_my -> MyFragment()
                else -> return@setOnItemSelectedListener false
            }

            // 현재 프래그먼트와 동일한 경우 변경하지 않음
            if (selectedFragment::class == currentFragment!!::class) return@setOnItemSelectedListener false

            replaceFragment(selectedFragment, true)
            currentFragment = selectedFragment
            true
        }
    }

    private fun replaceFragment(fragment: Fragment, addAnimation: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()

        if (addAnimation) {
            // **오른쪽에서 왼쪽으로 슬라이드 효과**
            transaction.setCustomAnimations(
                R.anim.slide_in_right, // 새 프래그먼트 들어올 때
                R.anim.slide_out_left  // 기존 프래그먼트 나갈 때
            )
        }

        transaction.replace(R.id.frame_container, fragment)
        transaction.commit()
    }
}
