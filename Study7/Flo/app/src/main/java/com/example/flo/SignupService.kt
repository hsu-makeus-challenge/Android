package com.example.flo

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface SignupService {
    @POST("/join")
    fun signUp(@Body request: SignUpRequest): Call<SignupResponse>
}
