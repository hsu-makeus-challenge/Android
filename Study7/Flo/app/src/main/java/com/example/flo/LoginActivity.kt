package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.flo.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. 이메일 도메인 드롭다운 설정
        val emailDomains = listOf("naver.com", "gmail.com", "hanmail.net", "daum.net", "kakao.com")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, emailDomains)
        binding.loginDirectInputEt.setAdapter(adapter)

        // 드롭다운 수동 표시
        binding.loginDirectInputEt.setOnClickListener {
            binding.loginDirectInputEt.showDropDown()
        }

        // 2. 로그인 버튼 클릭 시 login() 호출
        binding.loginSignInBtn.setOnClickListener {
            login()
        }

        // 3. 회원가입 화면 이동
        binding.loginSignUpTv.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // 4. 비밀번호 보기 토글
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

        // 입력값 검증
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
        val songDB = SongDatabase.getInstance(this)!!

        // 코루틴으로 DB 호출
        lifecycleScope.launch(Dispatchers.IO) {
            val user = songDB.userDao().getUser(email, pwd)

            withContext(Dispatchers.Main) {
                if (user != null) {
                    Log.d("LoginActivity", "로그인 성공: ${user.id}")
                    saveJwt(user.id)
                    startMainActivity()
                } else {
                    Toast.makeText(this@LoginActivity, "회원 정보가 존재하지 않습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveJwt(jwt: Int) {
        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit()
        editor.putInt("jwt", jwt)
        editor.apply()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // 로그인 후 로그인 액티비티 종료
    }
}
