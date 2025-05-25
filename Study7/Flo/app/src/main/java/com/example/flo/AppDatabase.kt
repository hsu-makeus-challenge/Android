package com.example.flo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Album::class, Song::class, LikeAlbum::class, LikeSong::class, User::class],
    version = 3,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun albumDao(): AlbumDao
    abstract fun songDao(): SongDao
    abstract fun likeAlbumDao(): LikeAlbumDao
    abstract fun likeSongDao(): LikeSongDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "song-database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
