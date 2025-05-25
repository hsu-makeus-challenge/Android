package com.example.flo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LikeAlbum")
data class LikeAlbum(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val albumId: Int,
    val isLiked: Int
)
