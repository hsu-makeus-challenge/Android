package com.example.study3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MusicAdapter(private val musicList: List<MusicItem>) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    inner class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val albumImage: ImageView = itemView.findViewById(R.id.albumImage) // 이미지 뷰 ID
        val albumTitle: TextView = itemView.findViewById(R.id.albumTitle) // 텍스트 뷰 ID
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_music, parent, false)
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val musicItem = musicList[position]
        holder.albumImage.setImageResource(musicItem.imageResId) // 앨범 이미지 설정
        holder.albumTitle.text = musicItem.title // 앨범 제목 설정
    }

    override fun getItemCount(): Int {
        return musicList.size
    }
}
