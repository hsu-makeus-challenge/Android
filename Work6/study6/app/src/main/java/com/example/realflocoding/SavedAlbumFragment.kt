package com.example.realflocoding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SavedAlbumFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SavedAlbumAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stack, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Adapter 초기화
        adapter = SavedAlbumAdapter(emptyList())
        recyclerView.adapter = adapter

        return view
    }

    fun updateSavedAlbums(songs: List<String>) {
        adapter.updateList(songs)
    }
}
