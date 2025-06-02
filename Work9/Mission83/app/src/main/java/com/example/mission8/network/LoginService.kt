package com.example.mission8.network

import com.example.mission8.model.LoginRequest
import com.example.mission8.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>
}