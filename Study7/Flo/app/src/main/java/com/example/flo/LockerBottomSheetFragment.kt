package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LockerBottomSheetFragment(private val listener: LockerActionListener) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_locker_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val playIv = view.findViewById<ImageView>(R.id.bottom_sheet_iv1)
        val playlistIv = view.findViewById<ImageView>(R.id.bottom_sheet_iv2)
        val myListIv = view.findViewById<ImageView>(R.id.bottom_sheet_iv3)
        val deleteIv = view.findViewById<ImageView>(R.id.bottom_sheet_iv4)

        playIv.setOnClickListener {
            listener.onPlaySelected()
            dismiss()
        }

        playlistIv.setOnClickListener {
            listener.onAddToPlaylistSelected()
            dismiss()
        }

        myListIv.setOnClickListener {
            listener.onMyListSelected()
            dismiss()
        }

        deleteIv.setOnClickListener {
            listener.onDeleteSelected()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        parentFragmentManager.setFragmentResult("bottom_sheet_closed", Bundle())
    }
}
