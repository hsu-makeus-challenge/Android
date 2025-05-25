package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemSongBinding

class SongAdapter(private val songs: MutableList<Song>) :
    RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    inner class SongViewHolder(val binding: ItemSongBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        with(holder.binding) {
            imageAlbumArt.setImageResource(song.albumArtResId)
            textTitle.text = song.title
            textArtist.text = song.artist

            buttonPlay.setOnClickListener {
                Toast.makeText(root.context, "${song.title} 재생", Toast.LENGTH_SHORT).show()
            }

            buttonMore.setOnClickListener {
                val pos = holder.adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    songs.removeAt(pos)
                    notifyItemRemoved(pos)
                }
            }
        }
    }

    override fun getItemCount() = songs.size
}
