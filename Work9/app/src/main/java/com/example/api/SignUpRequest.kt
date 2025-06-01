package com.example.api

// SignUpRequest.kt
data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String
)