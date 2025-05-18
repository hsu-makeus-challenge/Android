package com.example.mission3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mission3.databinding.ItemMymusicAlbumBinding

class MyMusicAlbumRVAdapter(private val songList: MutableList<Song>) :
    RecyclerView.Adapter<MyMusicAlbumRVAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMymusicAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songList[position])
        holder.binding.itemMymusicAlbumMoreIv.setOnClickListener {
            removeItem(position)
        }
    }

    override fun getItemCount(): Int = songList.size

    fun removeItem(position: Int) {
        songList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, songList.size)
    }

    inner class ViewHolder(val binding: ItemMymusicAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.itemMymusicAlbumTitleTv.text = song.title
            binding.itemMymusicAlbumSingerTv.text = song.singer
            binding.itemMymusicAlbumCoverImgIv.setImageResource(song.imageRes)
        }
    }
}
