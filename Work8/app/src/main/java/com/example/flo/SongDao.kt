package com.example.flo

import androidx.room.*

@Dao
interface SongDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(song: Songs)

    @Query("SELECT * FROM songTable")
    fun getAllSongs(): List<Songs>

    @Delete
    fun delete(song: Songs)

    @Query("DELETE FROM songTable")
    fun deleteAll()
}
