package com.example.floclone.data

data class LoginResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: MemberInfo?
)

data class MemberInfo(
    val memberId: Int,
    val accessToken: String
)