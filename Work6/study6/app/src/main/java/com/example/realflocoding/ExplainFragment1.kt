package com.example.realflocoding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExplainFragment1 : Fragment() {

    private lateinit var likeButton: ImageView
    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var albumDao: AlbumDao // 데이터베이스에서 앨범을 가져올 DAO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 레이아웃 Inflate
        val view = inflater.inflate(R.layout.v1_fragment, container, false)

        // RecyclerView 설정
        recyclerView = view.findViewById(R.id.recyclerview1)
        var savedAlbumAdapter = SavedAlbumAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = savedAlbumAdapter

        albumDao = AppDatabase.getDatabase(requireContext()).albumDao()
        val savedAlbumsLiveData: LiveData<List<Album>> = albumDao.getAllAlbums()


        // DividerItemDecoration 설정
        val dividerItemDecoration =
            DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        // "back" 버튼 클릭 리스너 설정
        val backButton: ImageView = view.findViewById(R.id.back)
        backButton.setOnClickListener {
            // HomeFragment로 돌아가기
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .addToBackStack(null)
                .commit()
        }

        // "Like" 하트 버튼 클릭 리스너 설정
        likeButton = view.findViewById(R.id.imagelike)
        likeButton.setOnClickListener {
            onLikeClick()
        }

        // "저장앨범" 탭을 설정합니다.
        tabLayout = view.findViewById(R.id.tabLayout2)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.text == "저장앨범") {
                    // "저장앨범" 탭이 선택되었을 때 RecyclerView에 저장된 앨범 리스트를 표시하도록 설정
                    loadSavedAlbums()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        return view
    }

    // 하트 버튼 클릭 시 호출되는 메소드
    private fun onLikeClick() {
        val album =
            Album(title = "앨범 제목", singer = "가수 이름", covering = R.drawable.v1) // 실제 앨범 정보로 설정
        val albumDao = AppDatabase.getDatabase(requireContext()).albumDao()

        if (likeButton.tag == "liked") {
            likeButton.setImageResource(R.drawable.ic_my_like_off)
            likeButton.tag = "unliked"

            // 앨범 삭제
            CoroutineScope(Dispatchers.IO).launch {
                albumDao.deleteAlbum(album)
            }
        } else {
            likeButton.setImageResource(R.drawable.ic_my_like_on)
            likeButton.tag = "liked"

            // 앨범 추가
            CoroutineScope(Dispatchers.IO).launch {
                albumDao.insertAlbum(album)
            }
        }
    }


    // 저장된 앨범 리스트를 로드하는 메소드
    private fun loadSavedAlbums() {
        val albumDao = AppDatabase.getDatabase(requireContext()).albumDao()
        CoroutineScope(Dispatchers.Main).launch {
            // 데이터베이스에서 저장된 앨범 목록 가져오기 (LiveData 반환)
            val savedAlbumsLiveData = withContext(Dispatchers.IO) {
                albumDao.getAllAlbums()  // LiveData<List<Album>> 반환
            }

            // LiveData를 관찰하여 데이터를 갱신하는 방법
            savedAlbumsLiveData.observe(viewLifecycleOwner, Observer { savedAlbums ->
                // LiveData에서 전달된 List<Album>을 어댑터에 전달
                recyclerView.adapter = SavedAlbumAdapter(savedAlbums)
            })
        }
    }





    // 앨범을 저장하는 메소드
    private fun saveAlbumToSaved(album: Album) {
        val sharedPreferences =
            requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // 예시: 하트 버튼 클릭 시 앨범 ID를 저장하는 방식
        editor.putBoolean("album_liked_${album.id}", true)
        editor.apply()

        showSaveConfirmationDialog()
    }

    // 앨범을 저장 목록에서 제거하는 메소드
// 앨범을 저장 목록에서 제거하는 메소드
    private fun removeAlbumFromSaved(album: Album) {
        val sharedPreferences = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // album.id를 사용하여 SharedPreferences에서 앨범을 제거
        editor.remove("album_liked_${album.id}")
        editor.apply()
    }


    // 앨범이 저장되었음을 사용자에게 확인시켜주는 다이얼로그
    private fun showSaveConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("앨범이 저장되었습니다.")
            .setPositiveButton("확인") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }

    private fun getAlbumFromDatabase(albumId: Int): Album {
        // 데이터베이스에서 앨범을 조회하는 로직
        // 예시: 데이터베이스에서 앨범 정보를 반환
        return Album(albumId, "앨범명", "아티스트명", R.drawable.v1) // 실제 데이터로 수정
    }
}



