package com.example.floclone.data

data class SignUpResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: Timestamp?
)

data class Timestamp(
    val memberId: Int,
    val createdAt: String,
    val updatedAt: String,
)