package com.example.study3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class album4fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.page_slide4, container, false)


        val imageView: ImageView = view.findViewById(R.id.imageView8)
        imageView.setOnClickListener {

            val explainFragment = ExplainFragment4()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, explainFragment)
                .addToBackStack(null) //
                .commit()
        }

        return view
    }
}
