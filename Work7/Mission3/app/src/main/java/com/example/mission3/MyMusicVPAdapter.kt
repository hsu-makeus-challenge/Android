package com.example.mission3

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyMusicVPAdapter (fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() : Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> SavedSongFragment()
            1 -> MusicFileFragment()
            else -> LikeFragment()
        }
    }
}