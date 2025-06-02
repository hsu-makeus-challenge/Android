package com.example.mission8.model

data class LoginResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: LoginResult?
)

data class LoginResult(
    val memberId: Int,
    val accessToken: String
)

