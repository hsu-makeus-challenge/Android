package com.example.flo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LikeSong(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val songId: Int
)
