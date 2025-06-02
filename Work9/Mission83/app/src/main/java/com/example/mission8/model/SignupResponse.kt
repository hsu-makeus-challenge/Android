package com.example.mission8.model

data class SignupResponse (
    val isSuccess: Boolean,
    val code : String,
    val message : String,
    val result : SuccessResponse?
)

data class SuccessResponse(
    val memberId : Int,
    val createdAt : String,
    val updateAt : String
)