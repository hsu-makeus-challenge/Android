package com.example.realflocoding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AlbumPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3 // 3개의 탭 (지정한 곡, 음악파일, 저장앨범)
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
//            0 -> SongFragment() // 지정한 곡
//            1 -> MusicFileFragment()
            2 -> SavedAlbumFragment() // 저장앨범
            else -> throw IllegalStateException("Invalid position: $position")
        }
    }
}
