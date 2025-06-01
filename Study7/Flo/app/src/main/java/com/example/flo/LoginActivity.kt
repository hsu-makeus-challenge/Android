package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityLoginBinding

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

        binding.loginSignInBtn.setOnClickListener {
            login()
        }

        binding.loginSignUpTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        var passwordVisible = false
        binding.loginHidePasswordIv.setOnClickListener {
            passwordVisible = !passwordVisible
            binding.loginPasswordEt.inputType = if (passwordVisible)
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.loginPasswordEt.setSelection(binding.loginPasswordEt.text.length)
        }

        binding.loginCloseIv.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.none, R.anim.slide_down)
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
}
