package com.example.floclone.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.floclone.Listener.SongAdapterListener
import com.example.floclone.data.Song
import com.example.floclone.R

class SongAdapter(private val songList: MutableList<Song>, private val listener: SongAdapterListener): RecyclerView.Adapter<SongAdapter.SongViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: SongViewHolder,
        position: Int
    ) {
        holder.bind(songList[position])
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    inner class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val songTitle: TextView = itemView.findViewById(R.id.item_song_title_tv)
        val songSinger: TextView = itemView.findViewById(R.id.item_song_singer_tv)
        val songImage: ImageView = itemView.findViewById(R.id.item_song_img_iv)
        private val moreBtn: ImageView = itemView.findViewById(R.id.item_song_more_iv)

        fun bind(song: Song){
            songTitle.text = song.title
            songSinger.text = song.singer
            songImage.setImageResource(song.imageResId)

            moreBtn.setOnClickListener {
                listener.onDeleteSong(adapterPosition)
            }
        }
    }

    fun removeAt(position: Int) {
        songList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, songList.size)
    }

}