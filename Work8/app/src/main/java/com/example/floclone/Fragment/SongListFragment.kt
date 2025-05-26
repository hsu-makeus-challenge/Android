package com.example.floclone.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.floclone.R
import com.example.floclone.databinding.FragmentHomeBinding
import com.example.floclone.databinding.FragmentSongListBinding

class SongListFragment : Fragment() {

    lateinit var binding: FragmentSongListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongListBinding.inflate(inflater, container, false)

        binding.songMixonTg.setOnClickListener{
            binding.songMixonTg.visibility = View.GONE
            binding.songMixoffTg.visibility = View.VISIBLE
        }
        binding.songMixoffTg.setOnClickListener{
            binding.songMixoffTg.visibility = View.GONE
            binding.songMixonTg.visibility = View.VISIBLE
        }

        return binding.root
    }

}