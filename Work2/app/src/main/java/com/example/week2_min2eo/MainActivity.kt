package com.example.week2_min2eo

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.week2_min2eo.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()

        // BottomNavigationView 클릭 이벤트 처리
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val selectedFragment = when (item.itemId) {
                R.id.home -> HomeFragment()
                R.id.diary -> DiaryFragment()
                R.id.calendar -> CalendarFragment()
                R.id.user -> UserFragment()
                else -> null
            }

            /*selectedFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it)
                    .commit()
            }
             */
            selectedFragment?.let {
                // 슬라이드 애니메이션 추가
                val slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_motion_1)
                val slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_motion_2)

                val transaction = supportFragmentManager.beginTransaction()

                // 이전 Fragment가 있으면 슬라이드가 없어지짐
                val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
                currentFragment?.view?.startAnimation(slideOut)

                // 새로운 Fragment가 슬라이드 들어옴
                it.view?.startAnimation(slideIn)

                transaction.replace(R.id.fragment_container, it)
                transaction.commit()
            }
            true
        }
    }
}