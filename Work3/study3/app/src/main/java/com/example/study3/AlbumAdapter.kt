package com.example.study3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.study3.R

class AlbumAdapter : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    // Sample list of songs by Vaundy
    private val songList = listOf(
        Pair("Vaundy", "Tokyo Flash"),
        Pair("Vaundy", "Odori"),
        Pair("Vaundy", "Shiwaawase"),
        Pair("Vaundy", "Fukakouryoku"),
        Pair("Vaundy", "Kaiju no Hanauta"),
        Pair("Vaundy", "Benefit"),
        Pair("Vaundy", "Sorafune")
    )

    // ViewHolder class for the RecyclerView items
    inner class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val artistNameTextView: TextView = itemView.findViewById(R.id.artistNameTextView)
        val songTitleTextView: TextView = itemView.findViewById(R.id.songTitleTextView)
    }

    // Inflate the layout for each item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return AlbumViewHolder(view)
    }

    // Bind the data to each item
    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val (artist, title) = songList[position]
        holder.artistNameTextView.text = artist
        holder.songTitleTextView.text = title
    }

    // Return the total number of items
    override fun getItemCount(): Int {
        return songList.size
    }
}
