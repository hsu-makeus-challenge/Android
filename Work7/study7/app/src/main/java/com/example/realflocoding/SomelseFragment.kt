package com.example.realflocoding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment


class SomelseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_somelse, container, false)

        // chartlabel 텍스트를 클릭했을 때 DialogFragment 띄우기
        val chartLabel: TextView = view.findViewById(R.id.chartLabel)
        chartLabel.setOnClickListener {
            // ChartDialogFragment를 다이얼로그 형태로 띄운다
            val dialogFragment = ChartDialogFragment()
            dialogFragment.show(parentFragmentManager, "ChartDialogFragment")
        }

        return view
    }

}
