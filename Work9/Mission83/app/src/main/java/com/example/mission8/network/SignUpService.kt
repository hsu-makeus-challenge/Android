package com.example.mission8.network

import com.example.mission8.model.SignUpRequest
import com.example.mission8.model.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST("/join")
    fun signup(
        @Body request: SignUpRequest
    ): Call<SignupResponse>
}