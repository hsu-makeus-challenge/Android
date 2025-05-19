package com.example.flo

import android.content.Intent
import android.os.*
import android.view.*
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

                albumAdapter.setOnItemClickListener(object : AlbumRVAdapter.OnItemClickListener {
                    override fun onItemClick(album: Album) {
                        val albumFragment = AlbumFragment()
                        val bundle = Bundle().apply {
                            putInt("albumId", album.id)
                            putString("title", album.title)
                            putString("singer", album.singer)
                            putInt("imageResId", album.coverImg)
                        }
                        albumFragment.arguments = bundle

                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, albumFragment)
                            .addToBackStack(null)
                            .commit()
                    }
                })

                albumAdapter.setOnPlayClickListener(object : AlbumRVAdapter.OnPlayClickListener {
                    override fun onPlayClick(album: Album) {
                        val intent = Intent(requireContext(), SongActivity::class.java)
                        intent.putExtra("albumId", album.id)
                        startActivity(intent)
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
