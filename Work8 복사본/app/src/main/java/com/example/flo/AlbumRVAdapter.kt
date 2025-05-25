package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemAlbumBinding

//만든 데이터리스트를 매개변수로 받아옴
class AlbumRVAdapter(private val albumList: ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick()
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener=itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.ViewHolder {
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    //onBindViewHolder: 데이터를 바인딩 할 때마다 호출되는 함수, 위 아래로 스크롤 할 때마다 호출
    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHolder, position: Int) {
        holder.bind(albumList[position])
        holder.itemView.setOnClickListener{ mItemClickListener.onItemClick() }
    }

    //데이터 세트 크기를 알려주는 함수 -> 마지막이 언제인지 알려줌
    override fun getItemCount(): Int = albumList.size

    //viewHolder클래스 생성, 틀 만들기
    inner class ViewHolder(val binding: ItemAlbumBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(album: Album){
            binding.itemAlbumTitleTv.text=album.title
            binding.itemAlbumSingerTv.text=album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)
        }
    }

}