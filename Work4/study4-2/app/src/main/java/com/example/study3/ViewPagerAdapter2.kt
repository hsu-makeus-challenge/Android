package com.example.study3

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter2(
    fragment: FragmentActivity,
    private val onClick: (Int) -> Unit
) : FragmentStateAdapter(fragment) {

    private val fragments = listOf(
        album1fragment(),
        album2fragment(),
        album3fragment(),
        album4fragment()
    )

    override fun getItemCount(): Int {
        return fragments.size // Fragment 수
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    // 클릭 리스너를 Fragment에서 설정할 수 있도록 하는 메서드 이게 참조해서 onclicklestner 을 하....
    fun setupClickListeners(view: View) {
        view.findViewById<ImageView>(R.id.imageView5)?.setOnClickListener {
            onClick(0)
            Log.d("ViewPagerAdapter2", "ImageView5 clicked - album1fragment")
        }
        view.findViewById<ImageView>(R.id.imageView6)?.setOnClickListener {
            onClick(1)
            Log.d("ViewPagerAdapter2", "ImageView6 clicked - album2fragment")
        }
        view.findViewById<ImageView>(R.id.imageView7)?.setOnClickListener {
            onClick(2)
            Log.d("ViewPagerAdapter2", "ImageView7 clicked - album3fragment")
        }
        view.findViewById<ImageView>(R.id.imageView8)?.setOnClickListener {
            onClick(3)
            Log.d("ViewPagerAdapter2", "ImageView8 clicked - album4fragment")
        }
    }
}
