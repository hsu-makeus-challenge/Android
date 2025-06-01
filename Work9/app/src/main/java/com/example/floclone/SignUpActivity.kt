package com.example.floclone

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.floclone.data.SignUpRequest
import com.example.floclone.databinding.ActivitySignUpBinding


class SignUpActivity : AppCompatActivity(), SignUpView {
    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpSignUpBtn.setOnClickListener {
            signUp()
        }
    }

    private fun getSignUpRequest() : SignUpRequest{
        val email: String = binding.signUpIdEt.text.toString() + "@" + binding.signUpDirectInputEt.text.toString()
        val pwd: String = binding.signUpPasswordEt.text.toString()
        val name: String = binding.signUpNameEt.text.toString()

        return SignUpRequest(name, email, pwd)
    }

    private fun signUp(){
        if(binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()){
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.",Toast.LENGTH_SHORT).show()
            return
        }

        if(binding.signUpNameEt.text.toString().isEmpty()){
            Toast.makeText(this,"이름 형식이 잘못되었습니다", Toast.LENGTH_SHORT).show()
            return
        }

        if(binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()){
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val authService = AuthService()
        authService.setSignUpView(this)

        authService.signUp(getSignUpRequest())
    }

    override fun onSignUpSuccess() {
        finish()
    }

    override fun onSignUpFailure() {
        TODO("Not yet implemented")
    }
}