package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

        val unlikeTv = view.findViewById<TextView>(R.id.locker_bottom_unlike_tv)
        val cancelTv = view.findViewById<TextView>(R.id.locker_bottom_cancel_tv)

        unlikeTv.setOnClickListener {
            listener.onUnlikeSelected()
            dismiss()
        }

        cancelTv.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        parentFragmentManager.setFragmentResult("bottom_sheet_closed", Bundle())
    }
}
