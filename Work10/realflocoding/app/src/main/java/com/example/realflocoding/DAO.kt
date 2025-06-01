package com.example.realflocoding

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlbumDao {
    @Insert
    suspend fun insertAlbum(album: Album)

    @Delete
    suspend fun deleteAlbum(album: Album)

    @Query("SELECT * FROM AlbumTable WHERE id = :albumId")
    suspend fun getAlbumById(albumId: Long): Album?

    @Query("SELECT * FROM AlbumTable")
    fun getAllAlbums(): LiveData<List<Album>>  // LiveData<List<Album>> 반환
}

@Dao
interface SongDao {
    @Insert
    suspend fun insertSong(song: SongItem)

    @Query("SELECT * FROM SongTable WHERE albumIdx = :albumId")
    suspend fun getSongsByAlbum(albumId: Int): List<SongItem>
}
