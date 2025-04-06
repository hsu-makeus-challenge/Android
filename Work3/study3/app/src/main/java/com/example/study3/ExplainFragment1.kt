package com.example.study3

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Random

val random = Random()
val num = random.nextInt(5)

class ExplainFragment1 : Fragment() {


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // v1_fragment.xml 레이아웃을 Inflate
        val view = inflater.inflate(R.layout.v1_fragment, container, false)

        // RecyclerView 초기화 및 Adapter 설정
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview1)
        recyclerView.layoutManager = LinearLayoutManager(context) // 레이아웃 매니저 설정
        recyclerView.adapter = AlbumAdapter() // Adapter 설정



        // DividerItemDecoration 추가
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        // "back" ImageView 클릭 리스너 설정
        val backButton: ImageView = view.findViewById(R.id.back)
        backButton.setOnClickListener {
            // HomeFragment로 전환
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()) // HomeFragment로 전환
                .addToBackStack(null) // 뒤로가기 가능하게 설정
                .commit()
        }

        return view
    }
}
