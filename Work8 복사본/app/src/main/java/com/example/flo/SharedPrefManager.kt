package com.example.flo

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)

    fun saveUserId(userId: Int) {
        prefs.edit().putInt("USER_ID", userId).apply()
    }

    fun getUserId(): Int {
        return prefs.getInt("USER_ID", -1)
    }

    fun clearUserId() {
        prefs.edit().remove("USER_ID").apply()
    }
}
