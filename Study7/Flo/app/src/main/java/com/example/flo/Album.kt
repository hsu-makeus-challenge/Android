package com.example.flo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Album(
    @PrimaryKey val id: Int, // 자동 생성 X
    val title: String,
    val singer: String,
    val coverImg: Int // drawable 리소스 ID
)
