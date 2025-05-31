package com.example.mission3

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LikedSong(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val singer: String,
    val imageRes: Int,
    val isLike: Boolean
)