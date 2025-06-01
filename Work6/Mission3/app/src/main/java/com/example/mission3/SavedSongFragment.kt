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

    private var albumDatas = ArrayList<Album>()
    lateinit var binding: FragmentSavedSongBinding
    private lateinit var songViewModel: SongViewModel

    private fun convertSongToAlbum(song: Song): Album {
        return Album(
            title = song.title,
            singer = song.singer,
            coverImage = song.imageRes
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedSongBinding.inflate(inflater, container, false)

        // ViewModel 연결
        songViewModel = ViewModelProvider(requireActivity())[SongViewModel::class.java]

        val sharedPreferences = requireActivity().getSharedPreferences("song_prefs", android.content.Context.MODE_PRIVATE)

        albumDatas.clear()
        for (song in songViewModel.songList) {
            val key = "${song.title}-${song.singer}"
            val isLiked = sharedPreferences.getBoolean(key, false)
            if (isLiked) {
                albumDatas.add(convertSongToAlbum(song))
            }
        }

        val adapter = MyMusicAlbumRVAdapter(albumDatas)
        binding.mymusicMusicAlbumRv.adapter = adapter
        binding.mymusicMusicAlbumRv.layoutManager = LinearLayoutManager(requireActivity())

        return binding.root
    }
}

