package com.example.mission3

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SongViewModel : ViewModel() {
    val currentSong = MutableLiveData<Song>()

    // 노래 목록 관리용 (필요 시)
    val songList = listOf(
        Song("Happy", "DAY6(데이식스)", 0, 190, false, false, R.drawable.happy),
        Song("I DO ME", "KiiiKiii (키키)", 0, 191, false, false, R.drawable.idome),
        Song("Drowning", "WOODZ", 0, 245, false, false, R.drawable.drowning)
    )
}

