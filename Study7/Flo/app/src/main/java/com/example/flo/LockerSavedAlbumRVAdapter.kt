package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemSavedAlbumBinding

class LockerSavedAlbumRVAdapter(
    private val albumList: ArrayList<Album>
) : RecyclerView.Adapter<LockerSavedAlbumRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onDeleteClick(album: Album)
    }

    private lateinit var myItemClickListener: MyItemClickListener

    fun setMyItemClickListener(listener: MyItemClickListener) {
        myItemClickListener = listener
    }

    inner class ViewHolder(val binding: ItemSavedAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.albumImgIv.setImageResource(album.coverImg)
            binding.albumTitleTv.text = album.title
            binding.albumArtistTv.text = album.singer
            binding.albumInfoTv.text = album.info

            binding.albumMoreIv.setOnClickListener {
                myItemClickListener.onDeleteClick(album)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSavedAlbumBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albumList[position])
    }

    override fun getItemCount(): Int = albumList.size
}
