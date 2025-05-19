package com.example.flo

data class LockerSong(
    val title: String,
    val singer: String,
    val albumInfo: String,
    var isPlaying: Boolean = false
)
