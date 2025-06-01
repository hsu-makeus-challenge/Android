package com.example.flo

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView

    fun setSignView(view: SignUpView) {
        this.signUpView = view
    }

    fun setLoginView(view: LoginView) {
        this.loginView = view
    }

    fun signUp(request: SignUpRequest) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.signUp(request).enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                val resp = response.body()
                if (resp?.code == 1000) signUpView.onSignUpSuccess()
                else signUpView.onSignUpFailure()
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                Log.d("SIGNUP/FAILURE", t.message.toString())
                signUpView.onSignUpFailure()
            }
        })
    }

    fun login(request: LoginRequest) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val resp = response.body()
                if (resp?.code == 1000 && resp.result != null) {
                    loginView.onLoginSuccess(resp.code, resp.result!!)
                } else {
                    loginView.onLoginFailure()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("LOGIN/FAILURE", t.message.toString())
                loginView.onLoginFailure()
            }
        })
    }
}
