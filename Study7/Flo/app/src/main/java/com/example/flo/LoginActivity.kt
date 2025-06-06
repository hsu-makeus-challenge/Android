package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityLoginBinding
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity(), LoginView {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val emailDomains = listOf("naver.com", "gmail.com", "hanmail.net", "daum.net", "kakao.com")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, emailDomains)
        binding.loginDirectInputEt.setAdapter(adapter)

        binding.loginDirectInputEt.setOnClickListener {
            binding.loginDirectInputEt.showDropDown()
        }

        binding.loginSignInBtn.setOnClickListener { login() }

        binding.loginSignUpTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        var passwordVisible = false
        binding.loginHidePasswordIv.setOnClickListener {
            passwordVisible = !passwordVisible
            binding.loginPasswordEt.inputType =
                if (passwordVisible) InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                else InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

            binding.loginPasswordEt.setSelection(binding.loginPasswordEt.text.length)
        }

        binding.loginCloseIv.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.none, R.anim.slide_down)
        }

        binding.loginKakakoLoginIv.setOnClickListener {
            loginWithKakao()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val uri = intent?.data
        Log.d("KAKAO", "onNewIntent 실행됨: $uri")

        if (uri != null && uri.toString().startsWith("kakao")) {
            val code = uri.getQueryParameter("code")
            if (code != null) {
                Log.d("KAKAO", "인가 코드: $code")

                // 인가 코드를 통해 accessToken 발급
                com.kakao.sdk.auth.AuthApiClient.instance.issueAccessToken(code) { token, error ->
                    if (error != null) {
                        Log.e("KAKAO", "토큰 발급 실패: ${error.localizedMessage}")
                    } else if (token != null) {
                        Log.d("KAKAO", "토큰 발급 성공: ${token.accessToken}")
                        fetchUserInfo()
                    }
                }
            } else {
                val error = uri.getQueryParameter("error")
                Log.e("KAKAO", "인가 코드 획득 실패: $error")
            }
        }
    }



    private fun login() {
        val id = binding.loginIdEt.text.toString().trim()
        val domain = binding.loginDirectInputEt.text.toString().trim()
        val pwd = binding.loginPasswordEt.text.toString()

        if (id.isEmpty() || domain.isEmpty()) {
            Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        if (!domain.contains(".") || domain.contains(",") || domain.contains(" ")) {
            Toast.makeText(this, "이메일 도메인을 정확히 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        if (pwd.isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val email = "$id@$domain"
        val request = LoginRequest(email, pwd)

        val authService = AuthService()
        authService.setLoginView(this)
        authService.login(request)
    }

    private fun saveJwt(jwt: String) {
        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        spf.edit().putString("jwt", jwt).apply()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onLoginSuccess(code: Int, result: MemberInfo) {
        saveJwt(result.accessToken)
        startMainActivity()
    }

    override fun onLoginFailure() {
        Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
    }

    private fun loginWithKakao() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e("KAKAO", "카카오톡 로그인 실패: ${error.message}")
                    loginWithKakaoAccount()
                } else if (token != null) {
                    Log.d("KAKAO", "카카오톡 로그인 성공: ${token.accessToken}")
                    fetchUserInfo()
                }
            }
        } else {
            loginWithKakaoAccount()
        }
    }

    private fun loginWithKakaoAccount() {
        UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
            if (error != null) {
                Log.e("KAKAO", "카카오 계정 로그인 실패: ${error.message}")
                Toast.makeText(this, "카카오 로그인 실패", Toast.LENGTH_SHORT).show()
            } else if (token != null) {
                Log.d("KAKAO", "카카오 계정 로그인 성공: ${token.accessToken}")
                fetchUserInfo()
            }
        }
    }

    private fun fetchUserInfo() {
        UserApiClient.instance.me { user, error ->
            if (user != null) {
                val nickname = user.kakaoAccount?.profile?.nickname ?: "닉네임 없음"
                val email = user.kakaoAccount?.email ?: "이메일 없음"
                val profileImage = user.kakaoAccount?.profile?.profileImageUrl ?: "이미지 없음"

                Log.d("KAKAO", "사용자 정보: $nickname / $email / $profileImage")

                // 실제 서버 로그인 처리 대신 임시로 JWT 저장
                saveJwt("KAKAO_FAKE_JWT")
                startMainActivity()
            } else {
                Log.e("KAKAO", "사용자 정보 요청 실패: ${error?.message}")
                Toast.makeText(this, "사용자 정보 요청 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
