package com.example.mission3

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LikedSong::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun likedSongDao(): LikedSongDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "liked_song_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}

