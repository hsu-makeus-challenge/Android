package com.example.mission3

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.Cursor
import android.content.ContentValues
import android.database.SQLException
import android.database.sqlite.SQLiteOpenHelper


class SongDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "songs.db"
        private const val DATABASE_VERSION = 1

        // 테이블 및 컬럼 이름
        const val TABLE_SONGS = "songs"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_SINGER = "singer"
        const val COLUMN_SECOND = "second"
        const val COLUMN_PLAYTIME = "playTime"
        const val COLUMN_ISPLAYING = "isPlaying"
        const val COLUMN_ISLIKE = "isLike"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_SONGS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT,
                $COLUMN_SINGER TEXT,
                $COLUMN_SECOND INTEGER,
                $COLUMN_PLAYTIME INTEGER,
                $COLUMN_ISPLAYING INTEGER,
                $COLUMN_ISLIKE INTEGER
            )
        """
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_SONGS")
        onCreate(db)
    }

    // Song 삽입 메서드
    fun insertSong(song: Song): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, song.title)
            put(COLUMN_SINGER, song.singer)
            put(COLUMN_SECOND, song.second)
            put(COLUMN_PLAYTIME, song.playTime)
            put(COLUMN_ISPLAYING, if (song.isPlaying) 1 else 0)
            put(COLUMN_ISLIKE, if (song.isLike) 1 else 0)
        }

        return db.insert(TABLE_SONGS, null, values)
    }

    // Song 업데이트 메서드
    fun updateSong(song: Song) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, song.title)
            put(COLUMN_SINGER, song.singer)
            put(COLUMN_SECOND, song.second)
            put(COLUMN_PLAYTIME, song.playTime)
            put(COLUMN_ISPLAYING, if (song.isPlaying) 1 else 0)
            put(COLUMN_ISLIKE, if (song.isLike) 1 else 0)
        }

        db.update(TABLE_SONGS, values, "$COLUMN_ID = ?", arrayOf(song.id.toString()))
    }

    // ID로 Song 조회 메서드
    @SuppressLint("Range")
    fun getSongById(id: Int): Song? {
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLE_SONGS,
            null,
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        return if (cursor.moveToFirst()) {
            val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
            val singer = cursor.getString(cursor.getColumnIndex(COLUMN_SINGER))
            val second = cursor.getInt(cursor.getColumnIndex(COLUMN_SECOND))
            val playTime = cursor.getInt(cursor.getColumnIndex(COLUMN_PLAYTIME))
            val isPlaying = cursor.getInt(cursor.getColumnIndex(COLUMN_ISPLAYING)) == 1
            val isLike = cursor.getInt(cursor.getColumnIndex(COLUMN_ISLIKE)) == 1
            val songId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))

            Song(title, singer, second, playTime, isPlaying, isLike, songId)
        } else {
            null
        }
    }

    // 모든 Song 조회 메서드
    @SuppressLint("Range")
    fun getAllSongs(): List<Song> {
        val songList = mutableListOf<Song>()
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLE_SONGS,
            null,
            null,
            null,
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
                val singer = cursor.getString(cursor.getColumnIndex(COLUMN_SINGER))
                val second = cursor.getInt(cursor.getColumnIndex(COLUMN_SECOND))
                val playTime = cursor.getInt(cursor.getColumnIndex(COLUMN_PLAYTIME))
                val isPlaying = cursor.getInt(cursor.getColumnIndex(COLUMN_ISPLAYING)) == 1
                val isLike = cursor.getInt(cursor.getColumnIndex(COLUMN_ISLIKE)) == 1
                val songId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))

                songList.add(Song(title, singer, second, playTime, isPlaying, isLike, songId))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return songList
    }

    // Song 삭제 메서드
    fun deleteSong(id: Int) {
        val db = writableDatabase
        db.delete(TABLE_SONGS, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }
}
