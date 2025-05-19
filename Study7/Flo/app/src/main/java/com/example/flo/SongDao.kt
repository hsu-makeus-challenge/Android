package com.example.flo

import androidx.room.*
import com.example.flo.Song

@Dao
interface SongDao {
    @Insert
    fun insertAll(songs: List<Song>)

    @Query("SELECT * FROM SongTable")
    fun getSongs(): List<Song>

    @Query("SELECT * FROM SongTable WHERE id = :id")
    suspend fun getSongById(id: Int): Song?

    @Query("SELECT * FROM SongTable WHERE isLike = 1")
    fun getLikedSongs(): List<Song>

    @Query("SELECT * FROM SongTable WHERE albumIdx = :albumId")
    fun getSongsByAlbum(albumId: Int): List<Song>

    @Query("UPDATE SongTable SET isLike = 0 WHERE isLike = 1")
    fun updateAllToUnliked()

    @Update
    fun update(song: Song)

    @Delete
    fun delete(song: Song)
}
