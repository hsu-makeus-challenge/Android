package com.example.mission3


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.mission3.databinding.FragmentHomeBinding
import java.util.Timer
import java.util.TimerTask

class HomeFragment : Fragment() {

    private lateinit var homeBannerVp: ViewPager2
    private lateinit var day6Iv: ImageView
    private lateinit var idomeIv: ImageView
    private lateinit var drowningIv: ImageView
    private lateinit var bannerAdapter: HomeBannerAdapter
    private lateinit var binding: FragmentHomeBinding

    private val bannerLayouts = listOf(
        R.layout.fragment_banner_1,
        R.layout.fragment_banner_2,
        R.layout.fragment_banner_3
    )

    private val songDuration = 191
    private var currentProcess = 0
    private var isPlaying = false // 플레이 상태 확인 변수
    private lateinit var timer: Timer
    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val seekBar = binding.miniPlayerSeekBar
        //seekBar.max = songDuration

        // Play/Pause 버튼 클릭 시 재생/일시 정지 처리
        binding.itemAlbumPlayImgIv.setOnClickListener {
            if (isPlaying) {
                pauseMusic()
            } else {
                startMusic()
            }
        }

        // mainPlayerCl 클릭 시 SongActivity로 이동
        binding.mainPlayerCl.setOnClickListener {
            val song = Song(
                title = binding.miniPlayerSongTitleTv.text.toString(),
                singer = binding.miniPlayerSingerTv.text.toString(),
                second = currentProcess,
                playTime = songDuration,
                isPlaying = isPlaying
            )

            val intent = Intent(requireContext(), SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
            startActivity(intent)
        }

        // ViewPager2 초기화 및 어댑터 설정
        homeBannerVp = binding.homeBannerVp
        bannerAdapter = HomeBannerAdapter(bannerLayouts) { position -> }
        homeBannerVp.adapter = bannerAdapter

        // 배너 자동 슬라이드 구현
        startBannerAutoSlide()

        // day6 ImageView 클릭 시 AlbumFragment로 이동
        day6Iv = binding.day6Iv
        day6Iv.setOnClickListener {
            val fragmentAlbum = AlbumFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, fragmentAlbum)
                .addToBackStack(null)
                .commit()
        }

        // idome ImageView 클릭 시 IdomeAlbumFragment로 이동
        idomeIv = binding.idomeIv
        idomeIv.setOnClickListener {
            val fragmentAlbumIdome = IdomeAlbumFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, fragmentAlbumIdome)
                .addToBackStack(null)
                .commit()
        }

        // drowning ImageView 클릭 시 IdomeAlbumFragment로 이동
        drowningIv = binding.drowningIv
        drowningIv.setOnClickListener {
            val fragmentAlbumDrowning = DrowningAlbumFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, fragmentAlbumDrowning)
                .addToBackStack(null)
                .commit()
        }
    }

    // 배너 자동 슬라이드 시작
    private fun startBannerAutoSlide() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                currentPage = (currentPage + 1) % 3  // 3개의 배너를 순환
                homeBannerVp.setCurrentItem(currentPage, true)
                handler.postDelayed(this, 3000)  // 3초마다 슬라이드
            }
        }, 3000)  // 3초 후 시작
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (::timer.isInitialized) timer.cancel()
    }

    // 음악 재생 시작
    private fun startMusic() {
        isPlaying = true
        binding.itemAlbumPlayImgIv.setImageResource(R.drawable.baseline_pause_24)  // Pause 아이콘으로 변경
        //startSeekBar()  // SeekBar 업데이트 시작
    }


    // 음악 일시 정지
    private fun pauseMusic() {
        isPlaying = false
        binding.itemAlbumPlayImgIv.setImageResource(R.drawable.baseline_play_24)  // Play 아이콘으로 변경
        if (::timer.isInitialized) timer.cancel()  // 타이머 정지
    }


    // SeekBar 업데이트 시작
//    private fun startSeekBar() {
//        timer = Timer()
//        timer.scheduleAtFixedRate(object : TimerTask() {
//            override fun run() {
//                if (currentProcess < songDuration) {
//                    currentProcess++
//                    handler.post {
//                        binding.miniPlayerSeekBar.progress = currentProcess
//                    }
//                } else {
//                    timer.cancel()  // 끝나면 타이머 취소
//                }
//            }
//        }, 0, 1000)  // 1초마다 진행
//    }
}