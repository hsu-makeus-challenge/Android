package com.example.mission3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mission3.databinding.FragmentSavedSongBinding

class SavedSongFragment : Fragment() {

    private var albumDatas = ArrayList<Album>()
    lateinit var binding: FragmentSavedSongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedSongBinding.inflate(inflater, container, false)

        albumDatas.apply {
            add(Album("HAPPY", "DAY6(데이식스)", R.drawable.happy))
            add(Album("I DO ME", "KiiiKiii (키키)", R.drawable.idome))
            add(Album("Drowning", "WOODZ", R.drawable.drowning))
            add(Album("Welcome to the show", "DAY6 (데이식스)", R.drawable.happy))
            add(Album("아니 근데 진짜", "LUCY", R.drawable.happy))
            add(Album("B.O.M.B", "TREASURE (트레저)", R.drawable.happy))
            add(Album("Supernova", "aespa", R.drawable.happy))
            add(Album("April shower", "세븐틴 (SEVENTEEN)", R.drawable.happy))
            add(Album("Blueming", "아이유 (IU)", R.drawable.happy))
        }

        val myMusicAlbumRVAdapter = MyMusicAlbumRVAdapter(albumDatas)
        binding.mymusicMusicAlbumRv.adapter = myMusicAlbumRVAdapter
        binding.mymusicMusicAlbumRv.layoutManager = LinearLayoutManager(requireActivity())

        return binding.root
    }
}

