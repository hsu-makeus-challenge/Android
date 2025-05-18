package com.example.realflocoding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonSignup: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 초기화
        editTextEmail = findViewById(R.id.emailLoginEditText)
        editTextPassword = findViewById(R.id.passwordLoginEditText)
        buttonLogin = findViewById(R.id.loginButton)
        buttonSignup = findViewById(R.id.signup)

        buttonSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // 로그인 버튼 클릭 시 처리
        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            // SharedPreferences에서 저장된 값 가져오기
            val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val storedEmail = sharedPref.getString("email", "")
            val storedPassword = sharedPref.getString("password", "")

            // 로그인 검증
            if (email == storedEmail && password == storedPassword) {
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()

                // 메인 화면으로 이동
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "이메일 또는 비밀번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
