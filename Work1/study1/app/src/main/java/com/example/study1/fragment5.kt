package com.example.study1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment


class fragment5 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment5, container, false)


        val backButton: ImageView = view.findViewById(R.id.back5)


        backButton.setOnClickListener {

            activity?.supportFragmentManager?.popBackStack()
        }

        return view
    }

}