package com.example.flo

import androidx.room.*

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(album: Album)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<Album>)

    @Query("SELECT * FROM Album")
    suspend fun getAllAlbums(): List<Album>

    @Query("SELECT * FROM Album WHERE id = :albumId")
    suspend fun getAlbumById(albumId: Int): Album?
}
