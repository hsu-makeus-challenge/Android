package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentLockerBinding
import kotlinx.coroutines.*

class LockerFragment : Fragment() {

    private var _binding: FragmentLockerBinding? = null
    private val binding get() = _binding!!

    private lateinit var lockerAdapter: LockerSongRVAdapter
    private val lockerList = ArrayList<Song>()

    private var isSelectionMode = false

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
            toggleSelectionMode()
        }

        // BottomSheet 닫히면 selection mode 해제
        parentFragmentManager.setFragmentResultListener("bottom_sheet_closed", viewLifecycleOwner) { _, _ ->
            isSelectionMode = false
            lockerAdapter.setSelectionMode(false)
            lockerAdapter.clearSelection()
            binding.lockerSaveImgIv.setImageResource(R.drawable.btn_editbar_addplaylist)
            lockerAdapter.notifyDataSetChanged()
        }
    }

    private fun toggleSelectionMode() {
        isSelectionMode = !isSelectionMode
        lockerAdapter.setSelectionMode(isSelectionMode)
        lockerAdapter.selectAll(isSelectionMode)
        lockerAdapter.notifyDataSetChanged()

        val icon = if (isSelectionMode) R.drawable.btn_editbar_delete else R.drawable.btn_editbar_addplaylist
        binding.lockerSaveImgIv.setImageResource(icon)

        if (isSelectionMode) {
            val bottomSheet = LockerBottomSheetFragment(object : LockerActionListener {
                override fun onPlaySelected() {
                    // SeekBar 재생만
                    val selected = lockerAdapter.getSelectedSongs().firstOrNull()
                    selected?.let { (activity as? MainActivity)?.setMiniPlayer(it) }
                }

                override fun onAddToPlaylistSelected() {
                    // 예시: 토스트 출력
                    showToast("재생목록 기능은 아직 미구현입니다.")
                }

                override fun onMyListSelected() {
                    // 예시: 토스트 출력
                    showToast("내 리스트 기능은 아직 미구현입니다.")
                }

                override fun onDeleteSelected() {
                    unlikeSelectedSongs()
                }
            })
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }
    }

    private fun unlikeSelectedSongs() {
        val db = SongDatabase.getInstance(requireContext())
        val selectedSongs = lockerAdapter.getSelectedSongs()

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            selectedSongs.forEach {
                db.songDao().update(it.copy(isLike = false))
            }
            withContext(Dispatchers.Main) {
                loadLikedSongs()
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

                binding.lockerEmptyTv.visibility =
                    if (lockerList.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    private fun initRecyclerView() {
        lockerAdapter = LockerSongRVAdapter(lockerList, object : LockerSongRVAdapter.OnSongClickListener {
            override fun onPlayClick(position: Int) {
                if (!isSelectionMode) {
                    for (i in lockerList.indices) {
                        lockerList[i] = lockerList[i].copy(isPlaying = i == position)
                    }
                    lockerAdapter.notifyDataSetChanged()
                    (activity as? MainActivity)?.setMiniPlayer(lockerList[position])
                }
            }

            override fun onMoreClick(position: Int) {
                if (!isSelectionMode) {
                    val db = SongDatabase.getInstance(requireContext())
                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                        db.songDao().update(lockerList[position].copy(isLike = false))
                        withContext(Dispatchers.Main) {
                            lockerList.removeAt(position)
                            lockerAdapter.notifyItemRemoved(position)
                        }
                    }
                }
            }
        })

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = lockerAdapter
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
