package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LockerFragment : Fragment() {

    private var _binding: FragmentLockerBinding? = null
    private val binding get() = _binding!!

    private lateinit var songAdapter: LockerAlbumRVAdapter
    private lateinit var albumAdapter: LockerSavedAlbumRVAdapter

    private val likedSongs = ArrayList<Song>()
    private val likedAlbums = ArrayList<Album>()

    private var isSelectionMode = false
    private var bottomSheetShown = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLockerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateLoginStatus()
        setupTabs()
        initSongRecyclerView()
        initAlbumRecyclerView()
        loadLikedSongs()

        binding.lockerSelectAllImgIv.setOnClickListener { toggleSelectionMode() }
        binding.lockerSelectAllTv.setOnClickListener { toggleSelectionMode() }
        binding.lockerPlayAllTv.setOnClickListener { playSongsInOrder() }
    }

    private fun setupTabs() {
        val tabLayout = binding.lockerContentTb
        tabLayout.addTab(tabLayout.newTab().setText("좋아요한 곡"))
        tabLayout.addTab(tabLayout.newTab().setText("내 리스트"))
        tabLayout.addTab(tabLayout.newTab().setText("저장앨범"))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        binding.recyclerView.adapter = songAdapter
                        loadLikedSongs()
                    }
                    2 -> {
                        binding.recyclerView.adapter = albumAdapter
                        loadLikedAlbums()
                    }
                    else -> showToast("미구현된 탭입니다")
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun toggleSelectionMode() {
        isSelectionMode = !isSelectionMode
        songAdapter.setSelectionMode(isSelectionMode)
        songAdapter.selectAll(isSelectionMode)
        songAdapter.notifyDataSetChanged()
    }

    private fun playSongsInOrder() {
        if (likedSongs.isNotEmpty()) {
            (activity as? MainActivity)?.setMiniPlayer(likedSongs[0])
        }
    }

    private fun loadLikedSongs() {
        val db = SongDatabase.getInstance(requireContext())
        val userId = getUserId()

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val songs = if (userId != -1) db.songDao().getLikedSongsByUser(userId) else emptyList()
            withContext(Dispatchers.Main) {
                likedSongs.clear()
                likedSongs.addAll(songs)
                songAdapter.notifyDataSetChanged()
                binding.lockerEmptyTv.visibility = if (likedSongs.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    private fun loadLikedAlbums() {
        val db = SongDatabase.getInstance(requireContext())
        val userId = getUserId()

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val albums = if (userId != -1) db.albumDao().getLikedAlbumsByUser(userId) else emptyList()
            withContext(Dispatchers.Main) {
                likedAlbums.clear()
                likedAlbums.addAll(albums)
                albumAdapter.notifyDataSetChanged()
                binding.lockerEmptyTv.visibility = if (likedAlbums.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    private fun initSongRecyclerView() {
        songAdapter = LockerAlbumRVAdapter(likedSongs).apply {
            setMyItemClickListener(object : LockerAlbumRVAdapter.MyItemClickListener {
                override fun onPlayClick(position: Int) {
                    if (!isSelectionMode) {
                        likedSongs.forEachIndexed { i, song ->
                            likedSongs[i] = song.copy(isPlaying = false)
                        }
                        notifyDataSetChanged()
                        (activity as? MainActivity)?.setMiniPlayer(likedSongs[position])
                    }
                }
                override fun onMoreClick(position: Int) {
                    val db = SongDatabase.getInstance(requireContext())
                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                        db.songDao().update(likedSongs[position].copy(isLike = false))
                        withContext(Dispatchers.Main) {
                            likedSongs.removeAt(position)
                            notifyItemRemoved(position)
                        }
                    }
                }
            })
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = songAdapter
    }

    private fun initAlbumRecyclerView() {
        albumAdapter = LockerSavedAlbumRVAdapter(likedAlbums).apply {
            setMyItemClickListener(object : LockerSavedAlbumRVAdapter.MyItemClickListener {
                override fun onDeleteClick(album: Album) {
                    val db = SongDatabase.getInstance(requireContext())
                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                        db.albumDao().update(album.copy(isLike = false))
                        withContext(Dispatchers.Main) {
                            likedAlbums.remove(album)
                            albumAdapter.notifyDataSetChanged()
                        }
                    }
                }
            })
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun getUserId(): Int {
        return requireContext().getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE).getInt("jwt", -1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        updateLoginStatus()
    }

    private fun updateLoginStatus() {
        val isLoggedIn = getUserId() != -1
        binding.lockerLoginTv.text = if (isLoggedIn) "로그아웃" else "로그인"
        binding.lockerLoginTv.setOnClickListener {
            if (isLoggedIn) {
                requireContext().getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE).edit().remove("jwt").apply()
                Toast.makeText(requireContext(), "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()
                binding.lockerLoginTv.text = "로그인"
                loadLikedSongs()
            } else {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
