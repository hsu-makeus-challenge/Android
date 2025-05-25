package com.example.flo

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FloApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val db = SongDatabase.getInstance(this)
        CoroutineScope(Dispatchers.IO).launch {
            if (db.songDao().getSongs().isEmpty()) {
                db.songDao().insertAll(getDummySongs())
            }
        }
    }

    private fun getDummySongs(): List<Song> {
        return listOf(
            Song(1, "라일락", "아이유", R.drawable.img_album_exp2, "가사1", "가사2", 0, 60, albumIdx = 2),
            Song(2, "Coin", "아이유", R.drawable.img_album_exp2, "가사1", "가사2", 0, 60, albumIdx = 2),
            Song(3, "Flu", "아이유", R.drawable.img_album_exp2, "가사1", "가사2", 0, 60, albumIdx = 2)
        )
    }
}
