package com.example.mission3

// HomeBannerAdapter.kt


import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class HomeBannerAdapter(
    private val bannerLayouts: List<Int>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<HomeBannerAdapter.BannerViewHolder>() {

    inner class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(position)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return bannerLayouts[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        // 이미지가 레이아웃 파일에 이미 설정되어 있으므로 별도의 바인딩이 필요하지 않을 수 있습니다.
        // 그러나 필요하다면 여기에 추가적인 바인딩 로직을 구현할 수 있습니다.
    }

    override fun getItemCount(): Int = bannerLayouts.size
}