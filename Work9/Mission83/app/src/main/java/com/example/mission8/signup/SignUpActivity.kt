package com.example.mission8.signup

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mission8.R
import com.example.mission8.model.SignUpRequest
import com.example.mission8.model.SuccessResponse
import com.example.mission8.network.NetworkService

/**
 *  SignUpActivity: 회원가입 화면
 *  - 사용자로부터 name/email/password 입력을 받아서
 *    NetworkService.signUp()을 호출 → 결과를 콜백으로 받아 화면에 토스트와 로그로 표시
 */
class SignUpActivity : AppCompatActivity(), SignUpContract.View {

    companion object {
        private const val TAG = "SignUpActivity"
    }

    // View 바인딩
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signUpButton: Button

    // NetworkService 객체 (API 호출 담당)
    private val networkService = NetworkService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // 1) UI 컴포넌트 바인딩
        nameEditText = findViewById(R.id.etName)
        emailEditText = findViewById(R.id.etEmail)
        passwordEditText = findViewById(R.id.etPassword)
        signUpButton = findViewById(R.id.btnSignUp)

        // 2) “회원가입” 버튼 클릭
        signUpButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // 필수 입력값 체크
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "입력값 부족: name='$name', email='$email', password='$password'")
                return@setOnClickListener
            }

            // 3) SignUpRequest 객체 생성
            val request = SignUpRequest(
                name = name,
                email = email,
                password = password
            )
            Log.d(TAG, "회원가입 요청: name='$name', email='$email'")

            // 4) NetworkService.signUp() 호출 → 콜백 결과: onSignUpSuccess / onSignUpFailure
            networkService.signUp(request, this)
        }
    }

    /**
     *  회원가입 성공 시 호출
     */
    override fun onSignUpSuccess(result: SuccessResponse) {
        runOnUiThread {
            Toast.makeText(
                this,
                "회원가입 성공! 회원 ID: ${result.memberId}",
                Toast.LENGTH_LONG
            ).show()
            Log.d(TAG, "회원가입 성공: memberId=${result.memberId}, createdAt=${result.createdAt}, updateAt=${result.updateAt}")
            // 가입 후 다른 화면으로 이동하거나 finish()를 호출하여 이전 화면으로 돌아갑니다.
            finish()
        }
    }

    /**
     *  회원가입 실패 시 호출
     */
    override fun onSignUpFailure(code: String, message: String) {
        runOnUiThread {
            Toast.makeText(
                this,
                "회원가입 실패: [$code] $message",
                Toast.LENGTH_LONG
            ).show()
            Log.d(TAG, "회원가입 실패: code=$code, message=$message")
        }
    }
}
