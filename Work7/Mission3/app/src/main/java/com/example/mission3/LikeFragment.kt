package com.example.mission3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mission3.databinding.FragmentLikeBinding
import com.example.mission3.databinding.FragmentSavedSongBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LikeFragment : Fragment() {

    private lateinit var db: AppDatabase
    private lateinit var likedSongDao: LikedSongDao

    private lateinit var binding: FragmentLikeBinding
    private val likedSongs = arrayListOf<LikedSong>()
    private lateinit var adapter: LikeAdapter // RecyclerView 어댑터

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLikeBinding.inflate(inflater, container, false)

        db = AppDatabase.getDatabase(requireContext())
        likedSongDao = db.likedSongDao()

        adapter = LikeAdapter(likedSongs)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        loadLikedSongs()

        return binding.root
    }

    private fun loadLikedSongs() {
        lifecycleScope.launch {
            val songs = likedSongDao.getAllLikedSongs()
            likedSongs.clear()
            likedSongs.addAll(songs)
            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
            }
        }
    }
}

