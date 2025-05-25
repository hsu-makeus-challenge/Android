package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.flo.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)

        binding.btnLogin.setOnClickListener {
            val email = binding.etLoginEmail.text.toString().trim()
            val password = binding.etLoginPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "이메일과 비밀번호를 모두 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val user = withContext(Dispatchers.IO) {
                    db.userDao().getUserByEmailAndPassword(email, password)
                }

                if (user != null) {
                    // 로그인 성공 시 SharedPreferences에 userId 저장
                    val sharedPrefManager = SharedPrefManager(this@LoginActivity)
                    sharedPrefManager.saveUserId(user.id)

                    Toast.makeText(this@LoginActivity, "로그인 성공!", Toast.LENGTH_SHORT).show()
                    // 메인 화면으로 이동
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()  // 로그인 화면 종료
                } else {
                    Toast.makeText(this@LoginActivity, "이메일 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
