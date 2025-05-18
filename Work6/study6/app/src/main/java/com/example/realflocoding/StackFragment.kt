package com.example.realflocoding

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout

class StackFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AlbumAdapter
    private lateinit var textViewEdit: TextView
    private lateinit var albumDao: AlbumDao
    private lateinit var textView7: TextView // TextView7 선언
    private lateinit var tabLayout: TabLayout

    private val albumList = mutableListOf<String>() // 저장앨범에 표시할 리스트
    private val likedSongs = mutableListOf<String>() // 좋아요 눌린 곡들 저장

    private val defaultSongList = mutableListOf(
        MusicItem(R.drawable.album_image1, title = "Tokyo Flash", artist = "Artist 1"),
        MusicItem(R.drawable.album_image2, title = "Odoriko", artist = "Vaundy"),
        MusicItem(R.drawable.album_image3, title = "Shiwaawase", artist = "Artist 2"),
        MusicItem(R.drawable.album_image4, title = "Fukakouryoku", artist = "Artist 3"),
        MusicItem(R.drawable.album_image5, title = "Kaiju no Hanauta", artist = "Artist 4"),
        MusicItem(R.drawable.album_image1, title = "Benefit", artist = "Artist 5"),
        MusicItem(R.drawable.album_image2, title = "Sorafune", artist = "Artist 6")
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stack, container, false)


        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Adapter 초기화
        adapter = AlbumAdapter(parentFragmentManager) // adapter 초기화
        recyclerView.adapter = adapter
        adapter.updateSongList(defaultSongList)

        albumDao = AppDatabase.getDatabase(requireContext()).albumDao()


        // Adapter에 데이터 설정
        // Divider 추가
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context, DividerItemDecoration.VERTICAL
        )
        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!
        )
        recyclerView.addItemDecoration(dividerItemDecoration)

        tabLayout = view.findViewById(R.id.tabLayout)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        // 지정한 곡 표시
                        adapter.updateSongList(defaultSongList)
                    }
                    1 -> {
                        // 음악 파일 (빈 리스트 표시)
                        adapter.updateSongList(emptyList())
                    }
                    2 -> {
                        // 저장 앨범 (albumList에 저장된 데이터 표시)
//                        val savedAlbums = albumList.map {
//                            MusicItem(R.drawable.flo, title = it, artist = "Unknown Artist")
//                        }
                        loadSavedAlbums()

                    }

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // 탭이 선택 해제될 때 호출 (필요 없으면 비워둠)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // 이미 선택된 탭이 다시 선택될 때 호출 (필요 없으면 비워둠)
            }
        })



        recyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                val childView = rv.findChildViewUnder(e.x, e.y)
                if (childView != null) {
                    val position = rv.getChildAdapterPosition(childView)
//                    val songId = adapter.songList[position].artist // 예시: songId로 artist 사용
//                    val isLiked = true // 실제 논리 추가 가능
//                    updateSongLikeStatus(songId, isLiked) // 클릭 시 콜백 처리
                      val songTitle = albumList[position] // 항목 데이터 가져오기
                      showPopupDialog(songTitle)
                    return true
                }
                return false
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })

        textViewEdit = view.findViewById(R.id.textViewEdit)
        textViewEdit.setOnClickListener {
            showToast("편집 기능 실행")
        }
        // TextView7 클릭 이벤트 설정
        textView7 = view.findViewById(R.id.textView7)
        textView7.setOnClickListener {
            navigateToLoginActivity()
        }

        return view
    }
    private fun loadSavedAlbums() {
        // 데이터베이스에서 저장된 앨범 목록 가져오기 (LiveData로 가져올 수 있음)
        val savedAlbumsLiveData = albumDao.getAllAlbums()

        savedAlbumsLiveData.observe(viewLifecycleOwner, Observer { savedAlbums ->
            // 앨범 데이터를 MusicItem 목록으로 변환
            val musicItems = savedAlbums.map { album ->
                // Album을 MusicItem으로 변환 (변환 로직은 Album과 MusicItem의 구조에 따라 다를 수 있음)
                MusicItem(album.id, album.title, album.singer)
            }

            // 앨범 데이터가 변경되면 RecyclerView에 전달
            albumList.clear() // 기존 리스트 초기화
//            albumList.addAll(musicItems) // 새로운 데이터 추가
            adapter.updateSongList(musicItems) // 어댑터 업데이트
        })
    }


    private fun showPopupDialog(songId: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("보관함 추가")
        builder.setMessage("이 곡을 보관함에 추가하시겠습니까?\n\n아티스트: $songId")
        builder.setPositiveButton("추가") { _, _ ->
            updateSongLikeStatus(songId, true)
        }
        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun updateSongLikeStatus(songId: String, isLiked: Boolean) {
        // DB 업데이트 로직 (예: Room 사용 시)
        // songId를 기반으로 isLiked 값을 업데이트
        if (isLiked) {
            Toast.makeText(context, "보관함에 추가되었습니다.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "보관함에서 제거되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun navigateToLoginActivity() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment, tag)
        transaction.commit()
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
