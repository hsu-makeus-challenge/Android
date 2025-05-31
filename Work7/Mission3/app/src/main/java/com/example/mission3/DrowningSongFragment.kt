package com.example.mission3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.example.mission3.databinding.FragmentSongBinding
import com.example.mission3.databinding.FragmentSongDrowningBinding
import com.example.mission3.databinding.FragmentSongIdomeBinding

class DrowningSongFragment : Fragment() {
    lateinit var binding: FragmentSongDrowningBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongDrowningBinding.inflate(inflater,container,false)
        return binding.root
    }
}