package com.example.floclone.Adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.floclone.Fragment.AlbumInfoFragment
import com.example.floclone.Fragment.AlbumVideoFragment
import com.example.floclone.Fragment.SongListFragment


class AlbumPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SongListFragment()
            1 -> AlbumInfoFragment()
            2 -> AlbumVideoFragment()
            else -> Fragment()
        }
    }
}
