package com.example.floclone.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.floclone.Adapter.SongAdapter
import com.example.floclone.Listener.SongAdapterListener
import com.example.floclone.R
import com.example.floclone.data.Song
import com.example.floclone.databinding.FragmentStorageBinding

class StorageFragment : Fragment(), SongAdapterListener {
    lateinit var binding: FragmentStorageBinding
    private lateinit var adapter: SongAdapter
    val songList = mutableListOf(
        Song("결론적으로", "SPARKY", 0, 60, false, R.drawable.img_album_exp),
        Song("선물", "코튼캔디", 0, 60, false, R.drawable.red),
        Song("별거 없던 그 하루로", "임창정", 0, 60, false, R.drawable.blue),
        Song("Bad Boy", "Red Velvet(레드벨벳)", 0, 60, false, R.drawable.yellow),
        Song("Always Me", "2am", 0, 60, false, R.drawable.gray),
        Song("잘 가라니", "2am", 0, 60, false, R.drawable.green),
        Song("라일락", "아이유", 0, 60, false, R.drawable.red),
        Song("ㅋㅋ", "ㄷㄷ", 0, 60, false, R.drawable.blue),
        Song("ㄴㄴ", "ㄹㄹ", 0, 60, false, R.drawable.yellow),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStorageBinding.inflate(inflater, container, false)

        adapter = SongAdapter(songList, this)
        binding.storageSongRv.adapter = adapter

        return binding.root
    }

    override fun onDeleteSong(position: Int) {
        adapter.removeAt(position)
    }

}