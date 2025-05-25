package com.example.flo

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.flo.databinding.ActivitySignUpBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. 이메일 도메인 자동완성 설정
        val emailDomains = listOf("naver.com", "daum.net", "gmail.com", "hanmail.net", "nate.com")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, emailDomains)
        binding.signUpDirectInputEt.setAdapter(adapter)
        binding.signUpDirectInputEt.setOnClickListener {
            binding.signUpDirectInputEt.showDropDown()
        }

        // 2. 비밀번호 보기 토글
        var passwordVisible = false
        binding.signUpHidePasswordIv.setOnClickListener {
            passwordVisible = !passwordVisible
            binding.signUpPasswordEt.inputType =
                if (passwordVisible)
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                else
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

            binding.signUpPasswordEt.setSelection(binding.signUpPasswordEt.text.length)
        }

        var passwordCheckVisible = false
        binding.signUpHidePasswordCheckIv.setOnClickListener {
            passwordCheckVisible = !passwordCheckVisible
            binding.signUpPasswordCheckEt.inputType =
                if (passwordCheckVisible)
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                else
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

            binding.signUpPasswordCheckEt.setSelection(binding.signUpPasswordCheckEt.text.length)
        }

        // 3. 가입 완료 버튼
        binding.signUpSignUpBtn.setOnClickListener {
            val result = signUp()
            if (result) finish()
        }
    }

    private fun getUser(): User {
        val id = binding.signUpIdEt.text.toString()
        val domain = binding.signUpDirectInputEt.text.toString().trim()
        val email = "$id@$domain"
        val pwd = binding.signUpPasswordEt.text.toString()
        return User(email, pwd)
    }

    private fun signUp(): Boolean {
        val id = binding.signUpIdEt.text.toString().trim()
        val domain = binding.signUpDirectInputEt.text.toString().trim()
        val pwd = binding.signUpPasswordEt.text.toString()
        val pwdCheck = binding.signUpPasswordCheckEt.text.toString()

        // 이메일 검증
        if (id.isEmpty() || domain.isEmpty()) {
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!domain.contains(".") || domain.contains(",") || domain.contains(" ")) {
            Toast.makeText(this, "이메일 도메인을 정확히 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }

        // 비밀번호 일치 확인
        if (pwd != pwdCheck) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            binding.signUpPasswordCheckEt.requestFocus()
            return false
        }

        val userDB = SongDatabase.getInstance(this)!!
        val newUser = getUser()

        lifecycleScope.launch(Dispatchers.IO) {
            val existingUser = userDB.userDao().getUserByEmail(newUser.email)
            if (existingUser != null) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@SignUpActivity, "이미 존재하는 이메일입니다.", Toast.LENGTH_SHORT).show()
                }
                return@launch
            }

            userDB.userDao().insert(newUser)

            val userList = userDB.userDao().getUsers()
            Log.d("sign-up", userList.toString())

            withContext(Dispatchers.Main) {
                Toast.makeText(this@SignUpActivity, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        return true
    }
}
