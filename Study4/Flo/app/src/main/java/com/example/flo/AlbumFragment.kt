package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {

    lateinit var binding: FragmentAlbumBinding

    // MIX 버튼 상태 저장용 변수
    private var isMixSelected = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        // HomeFragment에서 전달된 데이터 가져오기
        val title = arguments?.getString("title")
        val singer = arguments?.getString("singer")
        val imageResId = arguments?.getInt("imageResId") ?: R.drawable.img_album_exp2

        // 뷰에 데이터 반영
        binding.albumMusicTitleTv.text = title
        binding.albumSingerNameTv.text = singer
        binding.albumAlbumIv.setImageResource(imageResId)

        // 뒤로가기 버튼 누르면 HomeFragment로 복귀
        binding.albumBackIv.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .addToBackStack(null)
                .commit()
        }

        // 내 취향 MIX 버튼 클릭 시 이미지 변경
        binding.songMixTg.setOnClickListener {
            isMixSelected = !isMixSelected

            val imageRes = if (isMixSelected) {
                R.drawable.btn_toggle_on
            } else {
                R.drawable.btn_toggle_off
            }

            binding.songMixTg.setImageResource(imageRes)
        }

        // 클릭 시 SongActivity로 이동하도록 수정
        binding.songLalacLayout.setOnClickListener {
            goToSongActivity("Perfect Day", "소란")
        }

        binding.songFluLayout.setOnClickListener {
            goToSongActivity("Perfect Day (Inst)", "소란")
        }

        binding.songCoinLayout.setOnClickListener {
            goToSongActivity("가을목이", "소란")
        }

        binding.songSpringLayout.setOnClickListener {
            goToSongActivity("목소리", "소란")
        }

        binding.songCelebrityLayout.setOnClickListener {
            goToSongActivity("이제 나와라 고백", "소란")
        }

        return binding.root
    }

    private fun goToSongActivity(title: String, singer: String) {
        val intent = Intent(requireContext(), SongActivity::class.java)
        intent.putExtra("title", title)
        intent.putExtra("singer", singer)
        startActivity(intent)
    }
}
