package com.example.flo

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemSongBinding

class LockerAlbumRVAdapter(
    private val songList: ArrayList<Song>
) : RecyclerView.Adapter<LockerAlbumRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onPlayClick(position: Int)
        fun onMoreClick(position: Int)
    }

    private lateinit var itemClickListener: MyItemClickListener
    private var isSelectionMode = false
    private val selectedPositions = mutableSetOf<Int>()
    private var selectionChangedCallback: (() -> Unit)? = null

    fun setMyItemClickListener(listener: MyItemClickListener) {
        itemClickListener = listener
    }

    fun setSelectionMode(enabled: Boolean) {
        isSelectionMode = enabled
        if (!enabled) selectedPositions.clear()
        notifyDataSetChanged()
    }

    fun setSelectionChangedListener(callback: () -> Unit) {
        selectionChangedCallback = callback
    }

    fun selectAll(select: Boolean) {
        selectedPositions.clear()
        if (select) selectedPositions.addAll(songList.indices)
        notifyDataSetChanged()
        selectionChangedCallback?.invoke()
    }

    fun clearSelection() {
        selectedPositions.clear()
        notifyDataSetChanged()
        selectionChangedCallback?.invoke()
    }

    fun getSelectedSongs(): List<Song> = selectedPositions.map { songList[it] }

    inner class ViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song, position: Int) {
            binding.itemSongTitleTv.text = song.title
            binding.itemSongSingerTv.text = song.singer
            binding.itemSongImgIv.setImageResource(song.albumResId)

            // 선택된 항목 회색 배경
            binding.root.setBackgroundColor(
                if (selectedPositions.contains(position)) Color.LTGRAY else Color.TRANSPARENT
            )

            // 재생 상태 버튼은 리스트에서 항상 play 버튼으로 고정
            binding.itemSongPlayIv.setImageResource(R.drawable.btn_play)

            binding.itemSongPlayIv.setOnClickListener {
                itemClickListener.onPlayClick(position)
            }

            binding.itemSongMoreIv.setOnClickListener {
                itemClickListener.onMoreClick(position)
            }

            binding.root.setOnClickListener {
                if (isSelectionMode) {
                    if (selectedPositions.contains(position)) {
                        selectedPositions.remove(position)
                    } else {
                        selectedPositions.add(position)
                    }
                    notifyItemChanged(position)
                    selectionChangedCallback?.invoke()
                }
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
}
