package com.example.flo

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LockerFragment : Fragment() {

    private var _binding: FragmentLockerBinding? = null
    private val binding get() = _binding!!

    private lateinit var lockerAdapter: LockerAlbumRVAdapter
    private val lockerList = ArrayList<Song>()
    private var isSelectionMode = false
    private var bottomSheetShown = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLockerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabs()
        initRecyclerView()
        loadLikedSongs()

        binding.lockerSelectAllImgIv.setOnClickListener { toggleSelectionMode() }
        binding.lockerSelectAllTv.setOnClickListener { toggleSelectionMode() }
        binding.lockerPlayAllTv.setOnClickListener { playSongsInOrder() }

        parentFragmentManager.setFragmentResultListener("bottom_sheet_closed", viewLifecycleOwner) { _, _ ->
            isSelectionMode = false
            bottomSheetShown = false
            lockerAdapter.setSelectionMode(false)
            lockerAdapter.clearSelection()
            binding.lockerSaveImgIv.setImageResource(R.drawable.btn_editbar_addplaylist)
            lockerAdapter.notifyDataSetChanged()
        }
    }

    private fun setupTabs() {
        val tabLayout = binding.lockerContentTb
        tabLayout.addTab(tabLayout.newTab().setText("좋아요한 곡"))
        tabLayout.addTab(tabLayout.newTab().setText("내 리스트"))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // 실제로는 탭 전환 시 필터링 또는 화면 전환
                if (tab?.position == 0) loadLikedSongs()
                else showToast("미구현된 탭입니다")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun toggleSelectionMode() {
        isSelectionMode = !isSelectionMode
        lockerAdapter.setSelectionMode(isSelectionMode)
        lockerAdapter.selectAll(isSelectionMode)
        lockerAdapter.notifyDataSetChanged()

        val icon = if (isSelectionMode) R.drawable.btn_editbar_delete else R.drawable.btn_editbar_addplaylist
        binding.lockerSaveImgIv.setImageResource(icon)

        if (isSelectionMode && lockerAdapter.getSelectedSongs().isNotEmpty()) {
            showBottomSheet()
        }
    }

    private fun showBottomSheet() {
        if (bottomSheetShown) return
        bottomSheetShown = true

        val bottomSheet = LockerBottomSheetFragment(object : LockerActionListener {
            override fun onPlaySelected() {
                val selected = lockerAdapter.getSelectedSongs().firstOrNull()
                selected?.let { (activity as? MainActivity)?.setMiniPlayer(it) }
            }

            override fun onAddToPlaylistSelected() {
                showToast("재생목록 기능은 아직 미구현입니다.")
            }

            override fun onMyListSelected() {
                showToast("내 리스트 기능은 아직 미구현입니다.")
            }

            override fun onDeleteSelected() {
                unlikeSelectedSongs()
            }
        })
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
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

    private fun playSongsInOrder() {
        if (lockerList.isNotEmpty()) {
            (activity as? MainActivity)?.setMiniPlayer(lockerList[0])
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
        lockerAdapter = LockerAlbumRVAdapter(lockerList).apply {
            setMyItemClickListener(object : LockerAlbumRVAdapter.MyItemClickListener {
                override fun onPlayClick(position: Int) {
                    if (!isSelectionMode) {
                        lockerList.forEachIndexed { i, song ->
                            lockerList[i] = song.copy(isPlaying = false)
                        }
                        notifyDataSetChanged()
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
                                notifyItemRemoved(position)
                            }
                        }
                    }
                }
            })

            setSelectionChangedListener {
                if (getSelectedSongs().isNotEmpty() && isSelectionMode) {
                    showBottomSheet()
                }
            }
        }

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
