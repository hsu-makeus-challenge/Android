package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentBannerBinding

class BannerFragment : Fragment() {

    private lateinit var binding: FragmentBannerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBannerBinding.inflate(inflater, container, false)

        val imageRes = arguments?.getInt("imgRes") ?: R.drawable.img_home_viewpager_exp
        binding.bannerImageIv.setImageResource(imageRes)

        return binding.root
    }

    companion object {
        fun newInstance(imgRes: Int): BannerFragment {
            val fragment = BannerFragment()
            val bundle = Bundle()
            bundle.putInt("imgRes", imgRes)
            fragment.arguments = bundle
            return fragment
        }
    }
}
