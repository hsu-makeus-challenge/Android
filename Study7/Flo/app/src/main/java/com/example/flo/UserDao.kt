package com.example.flo

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    fun insert(user : User)

    @Query("select * from UserTable")
    fun getUsers() : List<User>

    @Query("select * from UserTable where email =:email and password = :password")
    fun getUser(email : String, password : String) : User?

    @Query("SELECT * FROM UserTable WHERE email = :email")
    fun getUserByEmail(email: String): User?
}