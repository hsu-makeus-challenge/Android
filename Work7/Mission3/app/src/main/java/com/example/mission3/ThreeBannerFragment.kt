package com.example.mission3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mission3.databinding.FragmentBanner3Binding

class ThreeBannerFragment : Fragment() {
    lateinit var binding : FragmentBanner3Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBanner3Binding.inflate(inflater,container,false)

        return binding.root
    }
}