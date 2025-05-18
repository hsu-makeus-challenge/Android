package com.example.realflocoding

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

class AlbumAdapter(
    private val fragmentManager: FragmentManager // 변수명 수정
) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    // Sample list of songs by Vaundy
    var songList = mutableListOf(
        MusicItem(R.drawable.album_image1, title = "Tokyo Flash", artist = "Artist 1"),
        MusicItem(R.drawable.album_image2, title = "Odoriko", artist = "Vaundy"),
        MusicItem(R.drawable.album_image3, title = "Shiwaawase", artist = "Artist 2"),
        MusicItem(R.drawable.album_image4, title = "Fukakouryoku", artist = "Artist 3"),
        MusicItem(R.drawable.album_image5, title = "Kaiju no Hanauta", artist = "Artist 4"),
        MusicItem(R.drawable.album_image1, title = "Benefit", artist = "Artist 5"),
        MusicItem(R.drawable.album_image2, title = "Sorafune", artist = "Artist 6")
    )
    fun convertStringToSongItem(title: String): SongItem {
        return SongItem(
            title = title,
            singer = "Default Singer",  // 필요한 경우 적절한 값을 할당
            playtime = 0,  // 필요한 경우 적절한 값을 할당
            inPlaying = false,
            music = "default_music",  // 필요한 경우 적절한 값을 할당
            covering = R.drawable.flo,  // 기본 이미지 리소스
            albumIdx = 0  // 기본 albumIdx 값
        )
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateSongList(newList: List<MusicItem>) {
        songList.clear()
        songList.addAll(newList)
        notifyDataSetChanged()
    }

    // ViewHolder class for the RecyclerView items
    inner class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val albumImage: ImageView = itemView.findViewById(R.id.albumImage)
        val albumTitle: TextView = itemView.findViewById(R.id.albumTitle)
        val removeImage: ImageView = itemView.findViewById(R.id.more)
    }

    // Inflate the layout for each item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_music, parent, false)
        return AlbumViewHolder(view)
    }

    // Bind the data to each item
    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val (artist, title) = songList[position]
        holder.albumImage.setImageResource(artist)
        holder.albumTitle.text = title

        // Remove item on click
        holder.removeImage.setOnClickListener {
            removeItem(holder.adapterPosition)
        }

        // 추가: 다른 버튼 클릭 시 이벤트 처리 (예: 미니 플레이어 이미지 클릭)
        holder.itemView.findViewById<ImageView>(R.id.imageView17).setOnClickListener {
            // Fragment 전환 로직 추가 예시
            // fragmentManager.beginTransaction()
            //     .replace(R.id.frame_container, YourFragment())
            //     .addToBackStack(null)
            //     .commit()
        }
    }

    // Remove item from the list
    private fun removeItem(position: Int) {
        songList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, songList.size)
    }

    // Return the total number of items
    override fun getItemCount(): Int {
        return songList.size
    }
}
