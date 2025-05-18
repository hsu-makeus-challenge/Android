package com.example.mission3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mission3.databinding.ItemLikedSongBinding

class LikeAdapter(private val likedSongs: List<LikedSong>) :
    RecyclerView.Adapter<LikeAdapter.LikeViewHolder>() {

    inner class LikeViewHolder(private val binding: ItemLikedSongBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(song: LikedSong) {
            binding.titleTextView.text = song.title
            binding.singerTextView.text = song.singer
            binding.imageView.setImageResource(song.imageRes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeViewHolder {
        val binding = ItemLikedSongBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LikeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LikeViewHolder, position: Int) {
        holder.bind(likedSongs[position])
    }

    override fun getItemCount(): Int = likedSongs.size
}
