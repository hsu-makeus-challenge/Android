package com.example.floclone.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.floclone.Adapter.SongAdapter
import com.example.floclone.Listener.SongAdapterListener
import com.example.floclone.LoginActivity
import com.example.floclone.MainActivity
import com.example.floclone.R
import com.example.floclone.data.Song
import com.example.floclone.data.SongDatabase
import com.example.floclone.databinding.FragmentStorageBinding

class StorageFragment : Fragment(), SongAdapterListener {
    lateinit var binding: FragmentStorageBinding
    private lateinit var adapter: SongAdapter
    lateinit var songDB: SongDatabase
    val songList = mutableListOf(
        Song("GONE", "릴러말즈", 0, 220, false, "music_gone", R.drawable.img_album_gone,false),
        Song("Welcome To The Show", "DAY6", 0, 210, false, "music_welcometotheshow", R.drawable.img_album_welcometotheshow,false),
        Song("MOON", "아이들", 0, 200, false, "music_moon", R.drawable.img_album_moon,false),
        Song("Thirsty", "aespa", 0, 190, false, "music_thirsty", R.drawable.img_album_thirsty,false),
        Song("사라지나요", "PATEKO", 0, 270, false, "music_pateko", R.drawable.img_album_pateko,false),
        Song("Drowning", "WOODZ", 0, 240, false, "music_drowning", R.drawable.img_album_drowning,false)

    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        songDB = SongDatabase.getInstance(requireContext())!!
        binding = FragmentStorageBinding.inflate(inflater, container, false)

        adapter = SongAdapter(songList, this)
        binding.storageSongRv.adapter = adapter
        adapter.addSongs(songDB.songDao().getLikedSong(true) as ArrayList<Song>)

        binding.lockerLoginTv.setOnClickListener{
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        return binding.root
    }

    override fun onStart(){
        super.onStart()
        initViews()
    }
    override fun onDeleteSong(position: Int) {
        adapter.removeAt(position)

    }

    private fun getJwt(): Int{
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)

        return spf!!.getInt("jwt",0)
    }

    private fun initViews(){
        val jwt : Int = getJwt()
        if(jwt == 0){
            binding.lockerLoginTv.text = "로그인"
            binding.lockerLoginTv.setOnClickListener{
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }
        else{
            binding.lockerLoginTv.text = "로그아웃"
            binding.lockerLoginTv.setOnClickListener{
                logout()
                startActivity(Intent(activity, MainActivity::class.java))
            }
        }
    }

    private fun logout(){
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()
        editor.remove("jwt")
        editor.apply()
    }
}