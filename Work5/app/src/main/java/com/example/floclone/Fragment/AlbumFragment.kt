package com.example.floclone.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.viewpager2.widget.ViewPager2
import com.example.floclone.Adapter.AlbumPagerAdapter
import com.example.floclone.R
import com.example.floclone.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AlbumFragment : Fragment(R.layout.fragment_album) {
    lateinit var binding: FragmentAlbumBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        binding.albumMusicTitleTv.text = arguments?.getString("albumTitle")
        binding.albumSingerNameTv.text = arguments?.getString("albumSinger")
        val imageResId = arguments?.getInt("albumImageResId")
        if (imageResId != null) {
            binding.albumAlbumIv.setImageResource(imageResId)
        }

        val tabLayout: TabLayout = binding.albumContentTl
        val viewPager: ViewPager2 = binding.albumContentVp

        // ViewPager2 어댑터 연결
        val pagerAdapter = AlbumPagerAdapter(this)
        viewPager.adapter = pagerAdapter

        // TabLayout과 ViewPager2 연결
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "곡"
                1 -> "상세정보"
                2 -> "영상"
                else -> ""
            }
        }.attach()

        return binding.root
    }

    private fun replaceFragment(fragment: Fragment){
        childFragmentManager.commit{
            replace(R.id.main_fl, fragment)
        }
    }
}