package com.example.mission8.login

import com.example.mission8.model.LoginResult

/**
 *  LoginContract: Activity(View)와 Service 사이의 인터페이스 정의
 */
interface LoginContract {
    interface View {
        /**
         *  로그인 성공 시 호출
         *  @param result  : LoginResult (userId, jwt, nickname 등)
         */
        fun onLoginSuccess(result: LoginResult)

        /**
         *  로그인 실패 시 호출
         *  @param code     : 서버 에러 코드 또는 통신 실패 시 키값
         *  @param message  : 사용자에게 보여줄 메시지
         */
        fun onLoginFailure(code: String, message: String)
    }
}
