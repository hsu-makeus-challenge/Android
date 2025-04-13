package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        // 내 취향 MIX 버튼 클릭 시 이미지 변경 (ImageView 하나만 사용하는 방식)
        binding.songMixTg.setOnClickListener {
            isMixSelected = !isMixSelected

            val imageRes = if (isMixSelected) {
                R.drawable.btn_toggle_on
            } else {
                R.drawable.btn_toggle_off
            }

            binding.songMixTg.setImageResource(imageRes)
        }

        // 곡 클릭 시 Toast 출력
        binding.songLalacLayout.setOnClickListener {
            Toast.makeText(activity, "Perfect Day", Toast.LENGTH_SHORT).show()
        }

        binding.songFluLayout.setOnClickListener {
            Toast.makeText(activity, "Perfect Day(Inst)", Toast.LENGTH_SHORT).show()
        }

        binding.songCoinLayout.setOnClickListener {
            Toast.makeText(activity, "가을목이", Toast.LENGTH_SHORT).show()
        }

        binding.songSpringLayout.setOnClickListener {
            Toast.makeText(activity, "목소리", Toast.LENGTH_SHORT).show()
        }

        binding.songCelebrityLayout.setOnClickListener {
            Toast.makeText(activity, "이제 나와라 고백", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}
