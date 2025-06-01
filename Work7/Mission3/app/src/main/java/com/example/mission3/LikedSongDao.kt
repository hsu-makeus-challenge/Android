package com.example.mission3

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LikedSongDao {

    @Query("SELECT * FROM LikedSong")
    suspend fun getAllLikedSongs(): List<LikedSong>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(song: LikedSong)

    // 필요한 경우 삭제 메서드도 추가할 수 있음
    @Delete
    suspend fun delete(song: LikedSong)
}


