package com.example.mission3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.example.mission3.databinding.FragMymusicBinding

class MyMusicFragment : Fragment() {
    private lateinit var binding: FragMymusicBinding
    private val information = arrayListOf("저장한곡", "음악파일")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragMymusicBinding.inflate(inflater, container, false)

        val lockerAdapter = MyMusicVPAdapter(this)
        binding.mymusicContentVp.adapter = lockerAdapter
        TabLayoutMediator(binding.mymusicContentTb, binding.mymusicContentVp) { tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }
}