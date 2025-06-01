package com.example.api

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.api.databinding.ActivitySignUpBinding
import kotlinx.coroutines.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val domain = binding.inputDomain.text.toString()
            val fullEmail = "$email@$domain"

            // 여기에 name 필드 값을 입력할 EditText가 없다면 임시로 email 앞부분으로 세팅
            val name = email  // 또는 원하는 임의값

            val password = binding.inputPassword.text.toString()
            val passwordConfirm = binding.inputPasswordConfirm.text.toString()

            if (password != passwordConfirm) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val signUpRequest = SignUpRequest(
                name = name,
                email = fullEmail,
                password = password
            )

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = ApiClient.apiService.signUp(signUpRequest)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@SignUpActivity, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                            finish()  // 가입 성공 시 이전 로그인 화면으로 돌아감
                        } else {
                            Toast.makeText(this@SignUpActivity, "실패: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@SignUpActivity, "에러 발생: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
