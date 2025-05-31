package com.example.mission3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mission3.databinding.RecyclerviewItemBinding

class RVAdapter(private val items: MutableList<String>) : RecyclerView.Adapter<RVAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RecyclerviewItemBinding = RecyclerviewItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    override fun getItemCount(): Int = items.size

    // ViewHolder 내부 클래스 정의
    inner class ViewHolder(private val binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItems(item: String) {
            binding.itemAlbumTitleTv.text = item
        }
    }
}
