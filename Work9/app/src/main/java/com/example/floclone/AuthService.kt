package com.example.floclone

import android.util.Log
import android.widget.Toast
import com.example.floclone.SignUpActivity
import com.example.floclone.data.LoginRequest
import com.example.floclone.data.LoginResponse
import com.example.floclone.data.SignUpRequest
import com.example.floclone.data.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView


    fun setSignUpView(signUpView: SignUpView){
        this.signUpView = signUpView
    }

    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }

    fun signUp(signUpRequest: SignUpRequest){
        val signUpService = NetworkModule.getClient().create(MemberService::class.java)

        val call = signUpService.signUp(signUpRequest)
        call.enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                Log.d("SIGNUP/SUCCESS", response.toString())

                val resp : SignUpResponse = response.body()!!
                when(resp.code){
                    "COMMON200" -> {
                        signUpView.onSignUpSuccess()
                    }
                    else -> signUpView.onSignUpFailure()
                }

            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                Log.d("SIGNUP/FAILURE", t.message.toString())
            }
        })
    }

    fun login(loginRequest: LoginRequest){
        val loginService = NetworkModule.getClient().create(MemberService::class.java)

        val call = loginService.login(loginRequest)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.d("LOGIN/SUCCESS", response.toString())

                val resp : LoginResponse = response.body()!!
                when(resp.code){
                    "COMMON200" -> {
                        loginView.onLoginSuccess(resp.result!!.accessToken, resp.result.memberId)
                        NetworkModule.setAccessToken(resp.result.accessToken)
                    }
                    else -> {
                        loginView.onLoginFailure()
                    }
                }

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("LOGIN/FAILURE", t.message.toString())
            }
        })
    }
}