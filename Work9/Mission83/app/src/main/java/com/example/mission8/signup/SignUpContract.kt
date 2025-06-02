package com.example.mission8.signup

import com.example.mission8.model.SuccessResponse

/**
 *  SignUpContract: Activity(View)와 Service 사이의 인터페이스 정의
 */
interface SignUpContract {
    interface View {
        /**
         *  회원가입 성공 시 호출
         *  @param result  : SuccessResponse (memberId, createdAt, updateAt)
         */
        fun onSignUpSuccess(result: SuccessResponse)

        /**
         *  회원가입 실패 시 호출
         *  @param code     : 서버 에러 코드 또는 통신 실패 시 키값 (ex. "NETWORK_ERROR")
         *  @param message  : 사용자에게 보여줄 메시지 (서버에서 내려온 메시지 또는 예외 메시지)
         */
        fun onSignUpFailure(code: String, message: String)
    }
}
