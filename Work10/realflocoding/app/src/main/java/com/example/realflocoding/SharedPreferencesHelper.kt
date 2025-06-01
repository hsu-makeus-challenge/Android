package com.example.realflocoding

import android.content.Context

object SharedPreferencesHelper {
    private const val PREF_NAME = "MusicData"

    fun saveMusicData(context: Context, time: Long, songTitle: String, artistName: String, isRunning: Boolean) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putLong("TIME", time)
            putString("SONG_TITLE", songTitle)
            putString("ARTIST_NAME", artistName)
            putBoolean("IS_RUNNING", isRunning)
            apply()
        }
    }

    fun loadMusicData(context: Context): Triple<Long, String, String> {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val time = sharedPreferences.getLong("TIME", 0L)
        val songTitle = sharedPreferences.getString("SONG_TITLE", "Odoriko") ?: "Odoriko"
        val artistName = sharedPreferences.getString("ARTIST_NAME", "Vaundy") ?: "Vaundy"
        return Triple(time, songTitle, artistName)
    }

    fun isTimerRunning(context: Context): Boolean {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getBoolean("IS_RUNNING", false)
    }
}
