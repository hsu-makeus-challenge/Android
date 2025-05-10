package com.example.floclone.data

import com.example.floclone.R

data class Song(
    var title : String = "",
    var singer : String = "",
    var second : Int = 0,
    var playTime : Int = 0,
    var isPlaying : Boolean = false,
    var imageResId: Int = R.drawable.img_album_exp
)
