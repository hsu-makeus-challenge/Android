package com.example.study2

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.study2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding을 사용하여 setContentView를 호출
        binding = ActivityMainBinding.inflate(layoutInflater) // binding 초기화
        setContentView(binding.root) // root view 설정

        // 첫 화면으로 activity_main.xml의 내용 표시
        if (savedInstanceState == null) {
            changeFragment(HomeFragment())
            // FragmentContainer에 Fragment를 추가하지 않고 activity_main.xml을 그대로 보여줌
        }

        val navView: BottomNavigationView = binding.navView // binding을 통해 BottomNavigationView 가져오기

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    changeFragment(HomeFragment())
                    true
                }
                R.id.navigation_dashboard -> {
                    changeFragment(DashboardFragment())
                    true
                }
                R.id.navigation_notifications -> {
                    changeFragment(NotificationsFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            // 커스텀 애니메이션 설정
            setCustomAnimations(
                R.anim.frg_transition_fade_in,  // enter 애니메이션
                R.anim.frg_transition_fade_out, // exit 애니메이션
                R.anim.frg_transition_fade_in,  // popEnter 애니메이션 (백 스택에서 돌아올 때)
                R.anim.frg_transition_fade_out  // popExit 애니메이션 (백 스택에서 나갈 때)
            )
            replace(R.id.fragment_container, fragment) // 프래그먼트 교체
            addToBackStack(fragment::class.java.simpleName) // 백 스택에 추가
            commit() // 트랜잭션 커밋
        }
    }

    private fun isColorDark(color: Int): Boolean {
        val darkness: Double =
            1 - (0.299 * android.graphics.Color.red(color) +
                    0.587 * android.graphics.Color.green(color) +
                    0.114 * android.graphics.Color.blue(color)) / 255
        return darkness >= 0.5
    }
}
