package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.gson.Gson
import kotlinx.coroutines.*

class AlbumFragment : Fragment() {

    private lateinit var binding: FragmentAlbumBinding
    private lateinit var songAdapter: SongRVAdapter
    private lateinit var likeAlbumDao: LikeAlbumDao  // 추가
    private var albumId: Int = -1
    private var isLiked: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        // DB DAO 초기화
        val db = SongDatabase.getInstance(requireContext())!!
        likeAlbumDao = db.likeAlbumDao()

        // 전달받은 앨범 정보
        albumId = arguments?.getInt("albumId") ?: -1
        val title = arguments?.getString("title") ?: ""
        val singer = arguments?.getString("singer") ?: ""
        val imageResId = arguments?.getInt("imageResId") ?: R.drawable.img_album_exp2

        // 화면 설정
        binding.albumMusicTitleTv.text = title
        binding.albumSingerNameTv.text = singer
        binding.albumAlbumIv.setImageResource(imageResId)

        binding.albumBackIv.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // 좋아요 초기화
        initLikeStatus()

        // 좋아요 토글
        binding.albumLikeIv.setOnClickListener {
            toggleLike()
        }

        // 전체듣기
        binding.songAllListenTv.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val songs = db.songDao().getSongsByAlbum(albumId)
                withContext(Dispatchers.Main) {
                    val intent = Intent(requireContext(), SongActivity::class.java)
                    intent.putExtra("songList", Gson().toJson(songs))
                    intent.putExtra("songId", songs.firstOrNull()?.id ?: -1)
                    startActivity(intent)
                }
            }
        }

        // 수록곡 불러오기
        loadSongs(albumId)

        return binding.root
    }

    private fun loadSongs(albumId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val songList = SongDatabase.getInstance(requireContext())
                .songDao().getSongsByAlbum(albumId)

            withContext(Dispatchers.Main) {
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
    }

    private fun initLikeStatus() {
        val userId = getUserId()
        if (userId == -1) return

        CoroutineScope(Dispatchers.IO).launch {
            val likedAlbums = likeAlbumDao.getLikedAlbums(userId)
            val liked = likedAlbums.any { it.albumId == albumId }

            withContext(Dispatchers.Main) {
                isLiked = liked
                binding.albumLikeIv.setImageResource(
                    if (isLiked) R.drawable.ic_like_on else R.drawable.ic_like_off
                )
            }
        }
    }

    private fun toggleLike() {
        val userId = getUserId()
        if (userId == -1) {
            Toast.makeText(requireContext(), "로그인 정보가 없습니다", Toast.LENGTH_SHORT).show()
            return
        }

        isLiked = !isLiked

        CoroutineScope(Dispatchers.IO).launch {
            likeAlbumDao.insertLikeAlbum(
                LikeAlbum(userId = userId, albumId = albumId, isLiked = if (isLiked) 1 else 0)
            )

            withContext(Dispatchers.Main) {
                binding.albumLikeIv.setImageResource(
                    if (isLiked) R.drawable.ic_like_on else R.drawable.ic_like_off
                )
            }
        }
    }

    private fun getUserId(): Int {
        return requireContext()
            .getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
            .getInt("jwt", -1)
    }
}
