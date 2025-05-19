package com.example.flo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemSongBinding

class LockerSongRVAdapter(
    private val songList: ArrayList<Song>,
    private val songClickListener: OnSongClickListener
) : RecyclerView.Adapter<LockerSongRVAdapter.ViewHolder>() {

    private var isSelectionMode = false
    private val selectedPositions = mutableSetOf<Int>()

    interface OnSongClickListener {
        fun onPlayClick(position: Int)
        fun onMoreClick(position: Int)
    }

    inner class ViewHolder(private val binding: ItemSongBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(song: Song, position: Int) {
            binding.itemSongTitleTv.text = song.title
            binding.itemSongSingerTv.text = song.singer
            binding.itemSongImgIv.setImageResource(song.albumResId)

            binding.itemSongPlayIv.setOnClickListener {
                songClickListener.onPlayClick(adapterPosition)
            }

            binding.itemSongMoreIv.setOnClickListener {
                songClickListener.onMoreClick(adapterPosition)
            }

            if (isSelectionMode && selectedPositions.contains(position)) {
                binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.colorPrimaryGrey))
            } else {
                binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, android.R.color.transparent))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songList[position], position)
    }

    override fun getItemCount(): Int = songList.size

    fun setSelectionMode(enabled: Boolean) {
        isSelectionMode = enabled
        if (!enabled) selectedPositions.clear()
    }

    fun selectAll(enabled: Boolean) {
        if (enabled) {
            selectedPositions.addAll(songList.indices)
        } else {
            selectedPositions.clear()
        }
        notifyDataSetChanged()
    }

    fun getSelectedSongs(): List<Song> {
        return selectedPositions.map { songList[it] }
    }

    fun clearSelection() {
        selectedPositions.clear()
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        songList.removeAt(position)
        notifyItemRemoved(position)
    }
}
