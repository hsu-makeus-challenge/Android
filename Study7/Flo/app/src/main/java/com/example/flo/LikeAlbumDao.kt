package com.example.flo

import androidx.room.*

@Dao
interface LikeAlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLikeAlbum(likeAlbum: LikeAlbum)

    @Query("SELECT * FROM LikeAlbum WHERE userId = :userId")
    fun getLikedAlbums(userId: Int): List<LikeAlbum>

    @Query("DELETE FROM LikeAlbum WHERE userId = :userId AND albumId = :albumId")
    fun deleteLikeAlbum(userId: Int, albumId: Int)
}
