package com.example.mission3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mission3.databinding.ItemMymusicAlbumBinding

class MyMusicAlbumRVAdapter(private val albumList: ArrayList<Album>) : RecyclerView.Adapter<MyMusicAlbumRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMymusicAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albumList[position])
        holder.binding.itemMymusicAlbumMoreIv.setOnClickListener {
            // 클릭 시 해당 아이템 삭제
            removeItem(position)
        }
    }

    override fun getItemCount(): Int = albumList.size

    fun removeItem(position: Int) {
        albumList.removeAt(position)  // 해당 아이템 제거
        notifyItemRemoved(position)   // 아이템 제거 후 갱신
        notifyItemRangeChanged(position, albumList.size)  // 나머지 아이템 갱신
    }

    inner class ViewHolder(val binding: ItemMymusicAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.itemMymusicAlbumTitleTv.text = album.title
            binding.itemMymusicAlbumSingerTv.text = album.singer
            binding.itemMymusicAlbumCoverImgIv.setImageResource(album.coverImage)
        }
    }
}
