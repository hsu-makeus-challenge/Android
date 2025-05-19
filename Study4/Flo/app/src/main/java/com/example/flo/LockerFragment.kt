package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentLockerBinding
import kotlinx.coroutines.*
import com.example.flo.LockerBottomSheetFragment


class LockerFragment : Fragment() {


    private var _binding: FragmentLockerBinding? = null
    private val binding get() = _binding!!

    private lateinit var lockerAdapter: LockerAlbumRVAdapter
    private val lockerList = ArrayList<Song>()  // Room Song 객체로 변경

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLockerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        loadLikedSongs()

        binding.lockerSelectAllImgIv.setOnClickListener {
            val bottomSheet = LockerBottomSheetFragment(object : LockerActionListener {
                override fun onUnlikeSelected() {
                    unlikeAllSongs()
                }
            })
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)

            // 저장 → 삭제 아이콘으로 변경
            binding.lockerSaveImgIv.setImageResource(R.drawable.btn_editbar_delete)

            // BottomSheet 닫히면 아이콘 복구
            parentFragmentManager.setFragmentResultListener("bottom_sheet_closed", viewLifecycleOwner) { _, _ ->
                binding.lockerSaveImgIv.setImageResource(R.drawable.btn_editbar_addplaylist)
            }
        }
    }

    private fun loadLikedSongs() {
        val db = SongDatabase.getInstance(requireContext())

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val likedSongs = db.songDao().getLikedSongs()

            withContext(Dispatchers.Main) {
                lockerList.clear()
                lockerList.addAll(likedSongs)
                lockerAdapter.notifyDataSetChanged()

                // 좋아요 곡 없을 때 텍스트뷰 표시
                binding.lockerEmptyTv.visibility =
                    if (lockerList.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    private fun initRecyclerView() {
        lockerAdapter = LockerAlbumRVAdapter(lockerList)

        lockerAdapter.setMyItemClickListener(object : LockerAlbumRVAdapter.MyItemClickListener {
            override fun onMoreClick(position: Int) {
                Toast.makeText(requireContext(), "삭제 기능은 미구현", Toast.LENGTH_SHORT).show()
            }

            override fun onPlayClick(position: Int) {
                for (i in lockerList.indices) {
                    lockerList[i] = lockerList[i].copy(isPlaying = i == position)
                }
                lockerAdapter.notifyDataSetChanged()

                val song = lockerList[position]
                (activity as? MainActivity)?.setMiniPlayer(song)
            }
        })

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = lockerAdapter
        }
    }

    private fun unlikeAllSongs() {
        val db = SongDatabase.getInstance(requireContext())

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            db.songDao().updateAllToUnliked()

            withContext(Dispatchers.Main) {
                loadLikedSongs()  // 좋아요 리스트 다시 로드
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
