package com.example.flo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Songs::class], version = 1)
abstract class SongDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao

    companion object {
        private var INSTANCE: SongDatabase? = null

        fun getInstance(context: Context): SongDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    SongDatabase::class.java,
                    "song-db"
                ).build()
            }
            return INSTANCE!!
        }
    }
}
