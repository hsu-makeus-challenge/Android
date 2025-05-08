package com.example.mission3

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyMusicVPAdapter (fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() : Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> SavedSongFragment()
            else -> MusicFileFragment()
        }
    }
}