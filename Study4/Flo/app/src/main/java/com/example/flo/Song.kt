package com.example.flo

data class Song(
    val title: String,
    val singer: String,
    val albumResId: Int = R.drawable.img_album_exp2,
    val lyrics1: String = "",
    val lyrics2: String = "",
    val second: Int = 0,
    val playTime: Int = 60,
    val isPlaying: Boolean = false
)
