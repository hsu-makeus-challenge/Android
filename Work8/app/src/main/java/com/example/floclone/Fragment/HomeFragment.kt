package com.example.floclone.Fragment

import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.floclone.Adapter.AlbumAdapter
import com.example.floclone.Adapter.BannerAdapter
import com.example.floclone.R
import com.example.floclone.data.Album
import com.example.floclone.databinding.FragmentHomeBinding
import android.os.Handler
import com.example.floclone.data.SongDatabase
import com.google.gson.Gson


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    val handler = Handler(Looper.getMainLooper())
    private var albumDatas = ArrayList<Album>()

    private lateinit var songDB : SongDatabase

    val images = listOf(R.drawable.img_first_album_default, R.drawable.img_today_popular_audio_exp)

    val slideRunnable = object : Runnable {
        override fun run() {
            val next = (binding.homePannelBackgroundVp.currentItem + 1) % images.size
            binding.homePannelBackgroundVp.currentItem = next
            handler.postDelayed(this, 3000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        songDB = SongDatabase.getInstance(requireContext())!!
        albumDatas.addAll(songDB.albumDao().getAlbums())

        val adapter = AlbumAdapter(albumDatas) { selectedAlbum ->
            val albumFragment = AlbumFragment().apply{
                arguments = Bundle().apply{
                    val albumJson = Gson().toJson(selectedAlbum)
                    putString("album",albumJson)
                }
            }

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_fl, albumFragment)
                .addToBackStack(null)
                .commit()
        }

        val bannerAdapter = BannerAdapter(images)
        binding.homePannelBackgroundVp.adapter = bannerAdapter
        binding.homePannelIndicator.setViewPager(binding.homePannelBackgroundVp)

        bannerAdapter.registerAdapterDataObserver(
            binding.homePannelIndicator.adapterDataObserver
        )

        handler.postDelayed(slideRunnable, 3000)
        binding.homeTodayMusicAlbumRv.adapter = adapter

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(slideRunnable)
    }
    override fun onResume() {
        super.onResume()
        handler.removeCallbacks(slideRunnable) // ✅ 기존 거 제거
        handler.postDelayed(slideRunnable, 3000) // ✅ 새로 등록
    }



}