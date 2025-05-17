package com.example.floclone.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.floclone.R
import com.example.floclone.data.Album

class AlbumAdapter(private val albumList: List<Album>, private  val onItemClick: (Album) -> Unit): RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    inner class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val albumTitle: TextView = itemView.findViewById(R.id.item_album_title_tv)
        val albumSinger: TextView = itemView.findViewById(R.id.item_album_singer_tv)
        val albumImage: ImageView = itemView.findViewById(R.id.item_album_cover_img_iv)

        fun bind(album: Album){
            albumTitle.text = album.title
            albumSinger.text = album.singer
            albumImage.setImageResource(album.imageResId)

            itemView.setOnClickListener{
                onItemClick(album)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int){
        holder.bind(albumList[position])
    }

    override fun getItemCount() = albumList.size
}