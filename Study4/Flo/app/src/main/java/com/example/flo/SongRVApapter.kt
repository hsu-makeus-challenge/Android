package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemSongBinding

class SongRVAdapter(private val songList: List<Song>) :
    RecyclerView.Adapter<SongRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onItemClick(song: Song)
        fun onPlaySong(song: Song)
    }

    private var listener: MyItemClickListener? = null

    fun setMyItemClickListener(listener: MyItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(val binding: ItemSongBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(song: Song) {
            binding.itemSongTitleTv.text = song.title
            binding.itemSongSingerTv.text = song.singer
            binding.itemSongImgIv.setImageResource(song.albumResId)

            // 전체 아이템 클릭
            binding.root.setOnClickListener {
                listener?.onItemClick(song)
            }

            // 재생 버튼 클릭
            binding.itemSongPlayIv.setOnClickListener {
                listener?.onPlaySong(song)
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
