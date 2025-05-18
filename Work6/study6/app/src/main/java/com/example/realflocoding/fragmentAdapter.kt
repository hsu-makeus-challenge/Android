package com.example.realflocoding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

class FragmentAdapter( // 클래스명 수정
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<FragmentAdapter.FragViewHolder>() {

    val songList = mutableListOf(
        MusicItem(R.drawable.album_image1, title = "Tokyo Flash", artist = "Artist 1"),
        MusicItem(R.drawable.album_image2, title = "Odoriko", artist = "Vaundy"),
        MusicItem(R.drawable.album_image3, title = "Shiwaawase", artist = "Artist 2"),
        MusicItem(R.drawable.album_image4, title = "Fukakouryoku", artist = "Artist 3"),
        MusicItem(R.drawable.album_image5, title = "Kaiju no Hanauta", artist = "Artist 4"),
        MusicItem(R.drawable.album_image1, title = "Benefit", artist = "Artist 5"),
        MusicItem(R.drawable.album_image2, title = "Sorafune", artist = "Artist 6")
    )

    inner class FragViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val albumImage: ImageView = itemView.findViewById(R.id.albumImage)
        val albumTitle: TextView = itemView.findViewById(R.id.albumTitle)
        val removeImage: ImageView = itemView.findViewById(R.id.more)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FragViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_music, parent, false)
        return FragViewHolder(view)
    }

    override fun onBindViewHolder(holder: FragViewHolder, position: Int) {
        val (artist, title) = songList[position]
        holder.albumImage.setImageResource(artist)
        holder.albumTitle.text = title

        holder.removeImage.setOnClickListener {
            removeItem(holder.adapterPosition)
        }

        holder.itemView.findViewById<ImageView>(R.id.imageView17).setOnClickListener {
            // Fragment 전환 로직
        }
    }

    private fun removeItem(position: Int) {
        songList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, songList.size)
    }

    override fun getItemCount(): Int = songList.size
}

