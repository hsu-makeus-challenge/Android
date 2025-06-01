package com.example.floclone

import com.example.floclone.data.LoginRequest
import com.example.floclone.data.LoginResponse
import com.example.floclone.data.SignUpRequest
import com.example.floclone.data.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MemberService {
    @POST("/join")
    fun signUp(@Body request: SignUpRequest): Call<SignUpResponse>

    @POST("/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}