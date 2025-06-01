package com.example.flo

interface LoginView {
    fun onLoginSuccess(code: Int, result: MemberInfo)
    fun onLoginFailure()
}
