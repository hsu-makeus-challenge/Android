package com.example.mission3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mission3.databinding.FragmentAlbumBinding
import com.example.mission3.databinding.FragmentAlbumDrowningBinding
import com.example.mission3.databinding.FragmentAlbumIdomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class DrowningAlbumFragment : Fragment() {

    lateinit var binding: FragmentAlbumDrowningBinding

    private val information = arrayListOf("수록곡", "상세정보", "영상")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumDrowningBinding.inflate(inflater, container, false)

        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main, HomeFragment())
                .commitAllowingStateLoss()
        }
        val albumAdapter = AlbumVPAdapterDrowning(this)
        binding.albumContentVp.adapter=albumAdapter
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) {
                tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }
}
