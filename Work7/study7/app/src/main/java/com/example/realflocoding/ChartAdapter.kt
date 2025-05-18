package com.example.realflocoding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChartAdapter : RecyclerView.Adapter<ChartAdapter.ChartViewHolder>() {

    private val chartItems = List(10) { "Item $it" }  // 임시 데이터

    class ChartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemText: TextView = view.findViewById(R.id.chartItemText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chart_item, parent, false)
        return ChartViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChartViewHolder, position: Int) {
        holder.itemText.text = chartItems[position]
    }

    override fun getItemCount(): Int {
        return chartItems.size
    }
}
