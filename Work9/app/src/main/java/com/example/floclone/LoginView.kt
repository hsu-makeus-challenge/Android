package com.example.floclone

interface LoginView {
    fun onLoginSuccess(accessToken: String, memberId: Int)
    fun onLoginFailure()
}