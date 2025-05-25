package com.example.flo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)




        albumDatas.apply{
            add(Album("Butter","방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Album("Lilac","아이유", R.drawable.img_album_exp2))

        }


        //어뎁터와 데이터 리스트 연결
        val albumRVAdapter = AlbumRVAdapter(albumDatas)

        albumRVAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener{
            override fun onItemClick() {
                //(context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm , AlbumFragment()).commitAllowingStateLoss()
            }

        })

        //recyclerview의 어뎁터를 연결
        binding.homeTodayMusicAlbumRv.adapter=albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager= LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)

        return binding.root
    }


}