package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.FragmentLockerBinding
import com.example.flo.databinding.FragmentSavedMusicBinding

class LockerFragment : Fragment() {
    private var _binding: FragmentSavedMusicBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val songList = mutableListOf(
            Song("Lilac", "아이유(IU)", R.drawable.img_album_exp2),
            Song("별거 없던 그 하루로", "임정정", R.drawable.img_album_exp),
        )

        val adapter = SongAdapter(songList)
        binding.recyclerViewSongs.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewSongs.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}