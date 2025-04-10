package com.example.floclone.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.floclone.R
import com.example.floclone.databinding.FragmentAlbumVideoBinding
import com.example.floclone.databinding.FragmentSongListBinding

class AlbumVideoFragment : Fragment() {
    lateinit var binding: FragmentAlbumVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumVideoBinding.inflate(inflater, container, false)

        return binding.root
    }
}