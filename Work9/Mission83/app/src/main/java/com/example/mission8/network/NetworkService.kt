package com.example.mission8.network

import com.example.mission8.login.LoginContract
import com.example.mission8.model.LoginRequest
import com.example.mission8.model.LoginResponse
import com.example.mission8.model.SignUpRequest
import com.example.mission8.model.SignUpResponse
import com.example.mission8.signup.SignUpContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *  NetworkService 클래스:
 *  - Activity(또는 Presenter)에서 호출하여 API 요청을 바로 보낼 수 있도록 래핑한 클래스
 *  - 회원가입(signUp)과 로그인(login)을 각각 처리
 */
class NetworkService {

    /**
     *  회원가입 API 호출
     *  @param request  : SignUpRequest (name, email, password)
     *  @param view     : SignUpContract.View (결과 콜백을 받을 인터페이스)
     */
    fun signUp(request: SignUpRequest, view: SignUpContract.View) {
        NetworkManager.signUpService.signup(request)
            .enqueue(object : Callback<SignUpResponse> {
                override fun onResponse(
                    call: Call<SignUpResponse>,
                    response: Response<SignUpResponse>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            if (body.isSuccess) {
                                // 회원가입 성공
                                view.onSignUpSuccess(body.result!!)
                            } else {
                                // 서버 로직 상 실패 (isSuccess == false)
                                view.onSignUpFailure(body.code, body.message)
                            }
                        } else {
                            // body가 null인 경우
                            view.onSignUpFailure("NULL_BODY", "서버 응답이 비었습니다.")
                        }
                    } else {
                        // HTTP 상태 코드 2xx가 아닌 경우
                        val codeStr = response.code().toString()
                        val msg = response.errorBody()?.string() ?: "알 수 없는 서버 오류"
                        view.onSignUpFailure(codeStr, msg)
                    }
                }

                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                    // 네트워크 통신 자체가 실패한 경우
                    view.onSignUpFailure("NETWORK_ERROR", t.message ?: "네트워크 오류")
                }
            })
    }

    /**
     *  로그인 API 호출
     *  @param request  : LoginRequest (email, password)
     *  @param view     : LoginContract.View (결과 콜백을 받을 인터페이스)
     */
    fun login(request: LoginRequest, view: LoginContract.View) {
        NetworkManager.loginService.login(request)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            if (body.isSuccess) {
                                // 로그인 성공
                                view.onLoginSuccess(body.result!!)
                            } else {
                                // 서버 로직 상 실패 (isSuccess == false)
                                view.onLoginFailure(body.code, body.message)
                            }
                        } else {
                            // body가 null인 경우
                            view.onLoginFailure("NULL_BODY", "서버 응답이 비었습니다.")
                        }
                    } else {
                        // HTTP 상태 코드 2xx가 아닌 경우
                        val codeStr = response.code().toString()
                        val msg = response.errorBody()?.string() ?: "알 수 없는 서버 오류"
                        view.onLoginFailure(codeStr, msg)
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    // 네트워크 통신 자체가 실패한 경우
                    view.onLoginFailure("NETWORK_ERROR", t.message ?: "네트워크 오류")
                }
            })
    }
}
