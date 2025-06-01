package com.example.mission8

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class User(
    val email: String,
    val password: String,
    val name: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

