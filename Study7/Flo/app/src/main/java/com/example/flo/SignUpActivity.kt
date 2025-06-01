package com.example.flo

import android.os.Bundle
import android.text.InputType
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity(), SignUpView {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val emailDomains = listOf("naver.com", "daum.net", "gmail.com", "hanmail.net", "nate.com")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, emailDomains)
        binding.signUpDirectInputEt.setAdapter(adapter)
        binding.signUpDirectInputEt.setOnClickListener {
            binding.signUpDirectInputEt.showDropDown()
        }

        var passwordVisible = false
        binding.signUpHidePasswordIv.setOnClickListener {
            passwordVisible = !passwordVisible
            binding.signUpPasswordEt.inputType = if (passwordVisible)
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.signUpPasswordEt.setSelection(binding.signUpPasswordEt.text.length)
        }

        var passwordCheckVisible = false
        binding.signUpHidePasswordCheckIv.setOnClickListener {
            passwordCheckVisible = !passwordCheckVisible
            binding.signUpPasswordCheckEt.inputType = if (passwordCheckVisible)
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.signUpPasswordCheckEt.setSelection(binding.signUpPasswordCheckEt.text.length)
        }

        binding.signUpSignUpBtn.setOnClickListener {
            validateAndSignUp()
        }
    }

    private fun validateAndSignUp() {
        val id = binding.signUpIdEt.text.toString().trim()
        val domain = binding.signUpDirectInputEt.text.toString().trim()
        val password = binding.signUpPasswordEt.text.toString()
        val passwordCheck = binding.signUpPasswordCheckEt.text.toString()

        if (id.isEmpty() || domain.isEmpty()) {
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (!domain.contains(".") || domain.contains(",") || domain.contains(" ")) {
            Toast.makeText(this, "이메일 도메인을 정확히 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != passwordCheck) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val email = "$id@$domain"
        val request = SignUpRequest(email, password)

        val authService = AuthService()
        authService.setSignView(this)
        authService.signUp(request)
    }

    override fun onSignUpSuccess() {
        Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onSignUpFailure() {
        Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
    }
}
