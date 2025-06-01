package com.example.realflocoding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView

class SavedAlbumAdapter(private var albums: List<Album>) : RecyclerView.Adapter<SavedAlbumAdapter.ViewHolder>() {

    // 데이터를 갱신하는 메소드
    fun updateAlbums(newAlbums: List<Album>) {
        albums = newAlbums
        notifyDataSetChanged() // 데이터 변경 시 RecyclerView 갱신
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_music, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = albums[position]
        holder.albumName.text = album.title
        holder.artistName.text = album.singer
        holder.coverImage.setImageResource(album.covering)
    }

    override fun getItemCount() = albums.size
    fun updateList(songs: List<String>) {

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val albumName: TextView = view.findViewById(R.id.songTitle)
        val artistName: TextView = view.findViewById(R.id.artistName)
        val coverImage: ImageView = view.findViewById(R.id.albumCover)
    }
}

