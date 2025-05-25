package com.example.flo

import androidx.room.*

@Dao
interface LikeSongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(likeSong: LikeSong)

    @Delete
    fun delete(likeSong: LikeSong)

    @Query("SELECT * FROM SongTable WHERE id IN (SELECT songId FROM LikeSong WHERE userId = :userId)")
    fun getLikedSongsByUser(userId: Int): List<Song>
}
