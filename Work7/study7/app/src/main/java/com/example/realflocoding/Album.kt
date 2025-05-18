package com.example.realflocoding

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AlbumTable")
data class Album(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val singer: String,
    val covering: Int
)
