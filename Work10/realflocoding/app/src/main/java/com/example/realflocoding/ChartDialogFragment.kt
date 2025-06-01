package com.example.realflocoding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChartDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.bottom_sheet_chart, container, false)

        // RecyclerView 설정 (예시)
        val recyclerView: RecyclerView = view.findViewById(R.id.chartRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        // 어댑터 설정 (예시: ChartAdapter 사용)
        recyclerView.adapter = ChartAdapter()

        return view
    }

}
