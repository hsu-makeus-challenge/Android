package com.example.study3


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ImageFragment : Fragment() {
    companion object {
        private const val ARG_LAYOUT = "arg_layout"

        fun newInstance(layoutId: Int) = ImageFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_LAYOUT, layoutId)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutId = arguments?.getInt(ARG_LAYOUT) ?: return null
        return inflater.inflate(layoutId, container, false) // XML 레이아웃 반환
    }
}
