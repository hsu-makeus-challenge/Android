package com.example.flo

data class SignUpResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: Timestamp?
)

data class Timestamp(
    val memberId: Int,
    val createdAt: String,
    val updatedAt: String
)
