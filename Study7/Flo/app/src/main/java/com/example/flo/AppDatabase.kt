package com.example.flo

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Song::class, Album::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun albumDao(): AlbumDao
}
