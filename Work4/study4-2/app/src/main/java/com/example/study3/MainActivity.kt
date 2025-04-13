package com.example.study3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var timer: Timer
    private lateinit var imageView: ImageView
    private lateinit var imagView1 : ImageView
    private val handler = Handler(Looper.getMainLooper())
    private val slideInterval: Long = 3000 // 3초마다 슬라이드

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView=findViewById(R.id.closebutton)
        imagView1=findViewById(R.id.imageViewbanner)

        imageView.setOnClickListener{
            imagView1.setTransitionVisibility(View.GONE);

        }

        // BottomNavigationView 설정
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

        // 첫 화면에 fragment_home 추가
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        // 이미지 클릭 리스너 설정
        val imageView3: ImageView = findViewById(R.id.listimage) // 이미지 뷰의 ID에 맞게 수정
        imageView3.setOnClickListener {
            // HomeActivity로 이동
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::timer.isInitialized) {
            timer.cancel() // 타이머 중지
        }
    }
}
