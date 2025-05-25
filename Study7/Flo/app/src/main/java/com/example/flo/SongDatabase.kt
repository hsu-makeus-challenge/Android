package com.example.flo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Album::class, Song::class, LikeAlbum::class, LikeSong::class, User::class],
    version = 5,
    exportSchema = false
)
abstract class SongDatabase : RoomDatabase() {

    abstract fun albumDao(): AlbumDao
    abstract fun songDao(): SongDao
    abstract fun likeAlbumDao(): LikeAlbumDao
    abstract fun likeSongDao(): LikeSongDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: SongDatabase? = null

        fun getInstance(context: Context): SongDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SongDatabase::class.java,
                    "song-database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
