package com.example.flo

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call

interface AuthRetrofitInterface {
    @POST("/users")
    fun signUp(@Body request: SignUpRequest): Call<SignUpResponse>

    @POST("/users/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}
