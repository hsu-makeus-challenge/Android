package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentSongBinding

class SongFragment : Fragment() {

    private var _binding: FragmentSongBinding? = null
    private val binding get() = _binding!!

    // 전달받은 데이터 저장 변수
    private var title: String? = null
    private var singer: String? = null
    private var imageResId: Int = R.drawable.img_album_exp2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            title = it.getString("title")
            singer = it.getString("singer")
            imageResId = it.getInt("imageResId", R.drawable.img_album_exp2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSongBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 전달받은 데이터로 UI 설정
        binding.songMusicTitleTv.text = title ?: "제목 없음"
        binding.songSingerNameTv.text = singer ?: "가수 없음"
        binding.songAlbumIv.setImageResource(imageResId)

        // 앨범 이미지 클릭 시 AlbumFragment로 이동
        binding.songAlbumIv.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, AlbumFragment())
                .addToBackStack(null)
                .commit()
        }

        // 뒤로가기 (하단 버튼 예시)
        binding.songDownIb?.setOnClickListener {
            parentFragmentManager.popBackStack()  // 이전 Fragment로 되돌아감
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(title: String, singer: String, imageResId: Int): SongFragment {
            val fragment = SongFragment()
            val args = Bundle().apply {
                putString("title", title)
                putString("singer", singer)
                putInt("imageResId", imageResId)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
