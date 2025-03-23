package com.example.androidstudy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class EmotionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_emotion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.happy_stamp)?.setOnClickListener {
            findNavController().navigate(R.id.fragment_happy)
        }

        view.findViewById<ImageView>(R.id.exite_stamp)?.setOnClickListener {
            findNavController().navigate(R.id.fragment_exite)
        }

        view.findViewById<ImageView>(R.id.normal_stamp)?.setOnClickListener {
            findNavController().navigate(R.id.fragment_normal)
        }

        view.findViewById<ImageView>(R.id.anxious_stamp)?.setOnClickListener {
            findNavController().navigate(R.id.fragment_anxious)
        }

        view.findViewById<ImageView>(R.id.angry_stamp)?.setOnClickListener {
            findNavController().navigate(R.id.fragment_angry)
        }
    }
}
