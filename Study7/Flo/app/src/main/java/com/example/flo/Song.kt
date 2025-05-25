package com.example.flo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SongTable")
data class Song(
    @PrimaryKey val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "singer")
    val singer: String,

    @ColumnInfo(name = "albumResId")
    val albumResId: Int,

    @ColumnInfo(name = "lyrics1")
    val lyrics1: String,

    @ColumnInfo(name = "lyrics2")
    val lyrics2: String,

    @ColumnInfo(name = "second")
    val second: Int,

    @ColumnInfo(name = "playTime")
    val playTime: Int,

    @ColumnInfo(name = "isPlaying")
    val isPlaying: Boolean = false,

    @ColumnInfo(name = "isLike")
    val isLike: Boolean = false,

    @ColumnInfo(name = "albumIdx")
    val albumIdx: Int
)
