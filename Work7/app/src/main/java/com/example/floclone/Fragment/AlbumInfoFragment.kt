package com.example.floclone.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.floclone.R
import com.example.floclone.databinding.FragmentAlbumInfoBinding
import com.example.floclone.databinding.FragmentSongListBinding

class AlbumInfoFragment : Fragment() {
    lateinit var binding: FragmentAlbumInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumInfoBinding.inflate(inflater, container, false)

        return binding.root
    }
}