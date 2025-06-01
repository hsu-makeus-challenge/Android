package com.example.flo

data class LoginResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: MemberInfo?
)

data class MemberInfo(
    val memberId: Int,
    val accessToken: String
)
