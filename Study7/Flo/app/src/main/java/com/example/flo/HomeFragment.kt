package com.example.flo

import android.content.Intent
import android.os.*
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentHomeBinding
import kotlinx.coroutines.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var bannerAdapter: BannerVPAdapter
    private val bannerHandler = Handler(Looper.getMainLooper())
    private var currentPage = 0

    private val bannerSlideRunnable = object : Runnable {
        override fun run() {
            if (bannerAdapter.itemCount > 0) {
                currentPage = (currentPage + 1) % bannerAdapter.itemCount
                binding.homeBannerVp.setCurrentItem(currentPage, true)
                bannerHandler.postDelayed(this, 3000)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAlbumList()
        initBanner()
    }

    private fun loadAlbumList() {
        val db = SongDatabase.getInstance(requireContext())!!

        // DB 접근을 백그라운드 코루틴에서 수행
        CoroutineScope(Dispatchers.IO).launch {
            val albums = db.albumDao().getAllAlbums()

            withContext(Dispatchers.Main) {
                val albumAdapter = AlbumRVAdapter(albums)

                albumAdapter.setOnPlayClickListener(object : AlbumRVAdapter.OnPlayClickListener {
                    override fun onPlayClick(album: Album) {
                        CoroutineScope(Dispatchers.IO).launch {
                            val db = SongDatabase.getInstance(requireContext())
                            val songs = db.songDao().getSongsByAlbum(album.id)
                            val firstSong = songs.firstOrNull()

                            if (firstSong != null) {
                                withContext(Dispatchers.Main) {
                                    val intent = Intent(requireContext(), SongActivity::class.java)
                                    intent.putExtra("songId", firstSong.id)
                                    startActivity(intent)
                                }
                            } else {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(requireContext(), "앨범에 수록곡이 없습니다", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                })

                albumAdapter.setOnPlayClickListener(object : AlbumRVAdapter.OnPlayClickListener {
                    override fun onPlayClick(album: Album) {
                        val db = SongDatabase.getInstance(requireContext())!!
                        CoroutineScope(Dispatchers.IO).launch {
                            val songs = db.songDao().getSongsByAlbum(album.id)
                            val firstSongId = songs.firstOrNull()?.id

                            withContext(Dispatchers.Main) {
                                if (firstSongId != null) {
                                    val intent = Intent(requireContext(), SongActivity::class.java)
                                    intent.putExtra("songId", firstSongId)
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(requireContext(), "수록곡이 없습니다", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                })

                binding.homeTodayMusicAlbumRv.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = albumAdapter
                }
            }
        }
    }

    private fun initBanner() {
        bannerAdapter = BannerVPAdapter(this)
        binding.homeBannerVp.adapter = bannerAdapter

        bannerAdapter.addFragment(BannerFragment.newInstance(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment.newInstance(R.drawable.img_home_viewpager_exp2))
        bannerAdapter.addFragment(BannerFragment.newInstance(R.drawable.img_home_viewpager_exp3))

        bannerHandler.postDelayed(bannerSlideRunnable, 3000)
    }

    override fun onResume() {
        super.onResume()
        bannerHandler.postDelayed(bannerSlideRunnable, 3000)
    }

    override fun onPause() {
        super.onPause()
        bannerHandler.removeCallbacks(bannerSlideRunnable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
