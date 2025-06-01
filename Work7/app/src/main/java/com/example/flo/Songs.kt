package com.example.flo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songTable")
data class Songs(
    val title: String,
    val singer: String,
    val coverImg: Int,

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)
