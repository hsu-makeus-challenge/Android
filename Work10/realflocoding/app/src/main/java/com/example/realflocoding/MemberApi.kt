package com.example.realflocoding


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MemberApi {
    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("join")
    fun signup(@Body request: SignUpRequest): Call<Void>
}