package com.example.api

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.api.databinding.ActivityLoginBinding
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 회원가입 텍스트 클릭시 회원가입 화면으로 이동
        binding.toSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val domain = binding.loginDomain.text.toString()
            val username = "$email@$domain"
            val password = binding.loginPassword.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = ApiClient.apiService.login(LoginRequest(username, password))
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            val token = response.body()?.token
                            Toast.makeText(this@LoginActivity, "로그인 성공! 토큰: $token", Toast.LENGTH_SHORT).show()

                            // 로그인 성공 시 MainActivity로 이동
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()  // 로그인 화면 종료
                        } else {
                            Toast.makeText(this@LoginActivity, "로그인 실패!", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, "에러: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
