package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.gson.Gson

class AlbumFragment : Fragment() {

    private lateinit var binding: FragmentAlbumBinding
    private lateinit var songAdapter: SongRVAdapter
    private var albumId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        // 전달받은 데이터
        albumId = arguments?.getInt("albumId") ?: -1
        val title = arguments?.getString("title") ?: ""
        val singer = arguments?.getString("singer") ?: ""
        val imageResId = arguments?.getInt("imageResId") ?: R.drawable.img_album_exp2

        // 뷰 세팅
        binding.albumMusicTitleTv.text = title
        binding.albumSingerNameTv.text = singer
        binding.albumAlbumIv.setImageResource(imageResId)

        binding.albumBackIv.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // 전체 듣기 버튼 클릭 시
        binding.songAllListenTv.setOnClickListener {
            val db = SongDatabase.getInstance(requireContext())!!
            val songs = db.songDao().getSongsByAlbum(albumId)

            val intent = Intent(requireContext(), SongActivity::class.java)
            val songListJson = Gson().toJson(songs)
            intent.putExtra("songList", songListJson)
            intent.putExtra("songId", songs.firstOrNull()?.id ?: -1)
            startActivity(intent)
        }

        // RecyclerView 연결
        loadSongs(albumId)

        return binding.root
    }

    private fun loadSongs(albumId: Int) {
        val db = SongDatabase.getInstance(requireContext())!!
        val songList = db.songDao().getSongsByAlbum(albumId)

        songAdapter = SongRVAdapter(songList)
        binding.albumSongRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = songAdapter
        }

        songAdapter.setMyItemClickListener(object : SongRVAdapter.MyItemClickListener {
            override fun onPlaySong(song: Song) {
                val intent = Intent(requireContext(), SongActivity::class.java)
                intent.putExtra("songId", song.id)
                startActivity(intent)
            }

            override fun onItemClick(song: Song) {
                Toast.makeText(requireContext(), "${song.title} 클릭됨", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
