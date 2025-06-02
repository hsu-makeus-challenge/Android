package com.example.mission8.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mission8.R
import com.example.mission8.model.LoginRequest
import com.example.mission8.model.LoginResult
import com.example.mission8.network.NetworkService

/**
 *  LoginActivity: 로그인 화면
 *  - 사용자로부터 email/password 입력을 받아서
 *    NetworkService.login()을 호출 → 결과를 콜백으로 받아 화면에 토스트와 로그로 표시
 */
class LoginActivity : AppCompatActivity(), LoginContract.View {

    companion object {
        private const val TAG = "LoginActivity"
    }

    // View 바인딩
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    // NetworkService 객체 (API 호출 담당)
    private val networkService = NetworkService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 1) UI 컴포넌트 바인딩
        emailEditText = findViewById(R.id.etEmail)
        passwordEditText = findViewById(R.id.etPassword)
        loginButton = findViewById(R.id.btnLogin)

        // 2) “로그인” 버튼 클릭
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // 필수 입력값 체크
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "이메일과 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "입력값 부족: email='$email', password='$password'")
                return@setOnClickListener
            }

            // 3) LoginRequest 객체 생성
            val request = LoginRequest(
                email = email,
                password = password
            )
            Log.d(TAG, "로그인 요청: email='$email'")

            // 4) NetworkService.login() 호출 → 콜백 결과: onLoginSuccess / onLoginFailure
            networkService.login(request, this)
        }
    }

    /**
     *  로그인 성공 시 호출
     */
    override fun onLoginSuccess(result: LoginResult) {
        runOnUiThread {
            Toast.makeText(
                this,
                "로그인 성공! 회원 ID: ${result.memberId}",
                Toast.LENGTH_LONG
            ).show()
            Log.d(TAG, "로그인 성공: memberId=${result.memberId}, accessToken=${result.accessToken}")

            // 예시: accessToken을 SharedPreferences에 저장
            // val pref = getSharedPreferences("user_pref", MODE_PRIVATE)
            // pref.edit().putString("accessToken", result.accessToken).apply()

            // 다음 화면(예: MainActivity)으로 이동
            // val intent = Intent(this, MainActivity::class.java)
            // startActivity(intent)
            // finish()
        }
    }

    /**
     *  로그인 실패 시 호출
     */
    override fun onLoginFailure(code: String, message: String) {
        runOnUiThread {
            Toast.makeText(
                this,
                "로그인 실패: [$code] $message",
                Toast.LENGTH_LONG
            ).show()
            Log.d(TAG, "로그인 실패: code=$code, message=$message")
        }
    }
}
