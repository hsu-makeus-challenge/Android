package com.example.mission3

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AlbumVPAdapterDrowning (fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> DrowningSongFragment()
            1 -> DetailFragment()
            else -> ViedoFragment()
        }
    }
}