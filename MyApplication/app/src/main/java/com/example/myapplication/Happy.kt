package com.example.study1

import android.os.Build.VERSION_CODES.R
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class Happy() : AppContentActivity() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.Happy, container, false)


        val backButton: ImageView = view.findViewById(R.id.back)


        backButton.setOnClickListener {

            activity?.supportFragmentManager?.popBackStack()
        }

        return view
    }

}