package com.example.mission3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mission3.databinding.FragmentSavedSongBinding

class SavedSongFragment : Fragment() {

    //private var albumDatas = ArrayList<Album>()
    lateinit var binding: FragmentSavedSongBinding
    private lateinit var songViewModel: SongViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedSongBinding.inflate(inflater, container, false)

        // ViewModel 연결
        songViewModel = ViewModelProvider(requireActivity())[SongViewModel::class.java]

        val adapter = MyMusicAlbumRVAdapter(songViewModel.songList.toMutableList())
        binding.mymusicMusicAlbumRv.adapter = adapter
        binding.mymusicMusicAlbumRv.layoutManager = LinearLayoutManager(requireActivity())

        return binding.root
    }
}

