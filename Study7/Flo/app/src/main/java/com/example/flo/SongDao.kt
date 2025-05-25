package com.example.flo

import androidx.room.*

@Dao
interface SongDao {

    @Insert
    fun insertAll(songs: List<Song>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLikeSong(likeSong: LikeSong)

    @Query("DELETE FROM LikeSong WHERE userId = :userId AND songId = :songId")
    fun deleteLikeSong(userId: Int, songId: Int)


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

    @Query("SELECT * FROM SongTable WHERE id IN (SELECT songId FROM LikeSong WHERE userId = :userId)")
    fun getLikedSongsByUser(userId: Int): List<Song>

    @Update
    fun update(song: Song)

    @Delete
    fun delete(song: Song)
}
