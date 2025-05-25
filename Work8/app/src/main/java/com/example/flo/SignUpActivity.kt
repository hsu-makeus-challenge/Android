package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.flo.databinding.ActivitySignUpBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)

        binding.btnSignUp.setOnClickListener {
            val email = binding.etSignUpEmail.text.toString().trim()
            val password = binding.etSignUpPassword.text.toString().trim()
            val confirmPassword = binding.etSignUpPasswordConfirm.text.toString().trim()  // 닉네임칸 → 비밀번호 확인란으로 사용

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val existingUser = withContext(Dispatchers.IO) {
                    db.userDao().getUserByEmail(email)
                }

                if (existingUser != null) {
                    Toast.makeText(this@SignUpActivity, "이미 존재하는 이메일입니다.", Toast.LENGTH_SHORT).show()
                } else {
                    val newUser = User(email = email, password = password)
                    withContext(Dispatchers.IO) {
                        db.userDao().insert(newUser)
                    }

                    Toast.makeText(this@SignUpActivity, "회원가입 완료!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                    finish()
                }
            }
        }
    }
}
