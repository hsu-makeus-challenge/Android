package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemAlbumBinding

class AlbumRVAdapter(private val albumList: List<Album>) :
    RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(album: Album) // 전체 영역 클릭
    }
    interface OnPlayClickListener {
        fun onPlayClick(album: Album) // ▶ 버튼만 클릭
    }

    private var listener: OnItemClickListener? = null
    private var playListener: OnPlayClickListener? = null

    fun setOnItemClickListener(l: OnItemClickListener) {
        listener = l
    }
    fun setOnPlayClickListener(p: OnPlayClickListener) {
        playListener = p
    }

    inner class ViewHolder(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg)

            // 전체 카드뷰 클릭 시
            binding.root.setOnClickListener {
                listener?.onItemClick(album)
            }

            // ▶ 버튼 클릭 시
            binding.itemAlbumPlayImgIv.setOnClickListener {
                playListener?.onPlayClick(album)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albumList[position])
    }

    override fun getItemCount(): Int = albumList.size
}
