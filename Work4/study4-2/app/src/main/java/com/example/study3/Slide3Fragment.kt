package com.example.study3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.study3.R

class SlideFragment3 : Fragment() {
    companion object {
        private const val ARG_TEXT = "arg_text"

        fun newInstance(text: String) = SlideFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_TEXT, text)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_slide3, container, false)

        return view
    }
}
