package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemSongBinding

class LockerSongRVAdapter(
    private val songList: ArrayList<Song>,
    private val songClickListener: OnSongClickListener
) : RecyclerView.Adapter<LockerSongRVAdapter.ViewHolder>() {

    interface OnSongClickListener {
        fun onPlayClick(position: Int)
        fun onMoreClick(position: Int)
    }

    inner class ViewHolder(private val binding: ItemSongBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(song: Song) {
            binding.itemSongTitleTv.text = song.title
            binding.itemSongSingerTv.text = song.singer
            binding.itemSongImgIv.setImageResource(song.albumResId)

            binding.itemSongPlayIv.setOnClickListener {
                songClickListener.onPlayClick(adapterPosition)
            }

            binding.itemSongMoreIv.setOnClickListener {
                songClickListener.onMoreClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songList[position])
    }

    override fun getItemCount(): Int = songList.size

    fun removeItem(position: Int) {
        songList.removeAt(position)
        notifyItemRemoved(position)
    }
}
