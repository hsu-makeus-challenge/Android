package com.example.flo

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

    fun setMyItemClickListener(listener: MyItemClickListener) {
        itemClickListener = listener
    }

    inner class ViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.itemSongTitleTv.text = song.title
            binding.itemSongSingerTv.text = song.singer
            binding.itemSongImgIv.setImageResource(song.albumResId)

            // 재생 상태에 따라 버튼 이미지 변경
            binding.itemSongPlayIv.setImageResource(
                if (song.isPlaying) R.drawable.btn_pause_32 else R.drawable.btn_play
            )

            binding.itemSongPlayIv.setOnClickListener {
                itemClickListener.onPlayClick(adapterPosition)
            }

            binding.itemSongMoreIv.setOnClickListener {
                itemClickListener.onMoreClick(adapterPosition)
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
}
