package com.example.mission3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mission3.databinding.FragmentBanner1Binding

class AllBannerFragment : Fragment() {
    lateinit var binding : FragmentBanner1Binding // 이름 변경
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBanner1Binding.inflate(inflater, container, false) // 이름 변경
        return binding.root
    }
}

