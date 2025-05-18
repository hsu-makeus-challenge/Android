package com.example.realflocoding

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "SongTable",
    foreignKeys = [
        ForeignKey(
            entity = Album::class,
            parentColumns = ["id"],
            childColumns = ["albumIdx"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SongItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val singer: String,
    val playtime: Int,
    val inPlaying: Boolean,
    val music: String,
    val covering: Int,
    val albumIdx: Int // AlbumTable과 연결
)
