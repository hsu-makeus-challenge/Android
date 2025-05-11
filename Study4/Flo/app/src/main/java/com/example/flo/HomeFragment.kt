package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentHomeBinding

import android.util.Log

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // home_album_01 클릭 시 AlbumFragment로 이동
        binding.homeAlbum01Iv.setOnClickListener {
            Log.d("HomeFragment", "앨범 클릭됨")
            val albumFragment = AlbumFragment()

            val bundle = Bundle().apply {
                putString("title", "Perfect Day")
                putString("singer", "소란")
                putInt("imageResId", R.drawable.img_album_exp2)
            }
            albumFragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, albumFragment)
                .addToBackStack(null)
                .commit()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
