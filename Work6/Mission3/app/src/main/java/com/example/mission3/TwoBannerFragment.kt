package com.example.mission3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mission3.databinding.FragmentBanner2Binding

class TwoBannerFragment : Fragment() {
    lateinit var binding : FragmentBanner2Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBanner2Binding.inflate(inflater,container,false)

        return binding.root
    }
}