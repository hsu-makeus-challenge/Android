package com.example.flo

import android.view.LayoutInflater
import android.view.View
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

    private val selectedPositions = mutableSetOf<Int>()
    var isSelectionMode = false

    inner class ViewHolder(private val binding: ItemSongBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(song: Song, position: Int) {
            binding.itemSongTitleTv.text = song.title
            binding.itemSongSingerTv.text = song.singer
            binding.itemSongImgIv.setImageResource(song.albumResId)

            // 선택 모드 UI 처리
            binding.itemSongSelectIv.visibility = if (isSelectionMode) View.VISIBLE else View.GONE
            binding.itemSongSelectIv.setImageResource(
                if (selectedPositions.contains(position)) R.drawable.btn_select_on
                else R.drawable.btn_select_off
            )

            binding.itemSongSelectIv.setOnClickListener {
                toggleSelection(position)
            }

            binding.itemSongPlayIv.setOnClickListener {
                if (!isSelectionMode) {
                    songClickListener.onPlayClick(adapterPosition)
                } else {
                    toggleSelection(position)
                }
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
        holder.bind(songList[position], position)
    }

    override fun getItemCount(): Int = songList.size

    fun removeItem(position: Int) {
        songList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getSelectedSongs(): List<Song> {
        return selectedPositions.map { songList[it] }
    }

    fun clearSelections() {
        selectedPositions.clear()
        notifyDataSetChanged()
    }

    fun toggleSelection(position: Int) {
        if (selectedPositions.contains(position)) {
            selectedPositions.remove(position)
        } else {
            selectedPositions.add(position)
        }
        notifyItemChanged(position)
    }

    fun enableSelectionMode(enable: Boolean) {
        isSelectionMode = enable
        if (!enable) selectedPositions.clear()
        notifyDataSetChanged()
    }
}
