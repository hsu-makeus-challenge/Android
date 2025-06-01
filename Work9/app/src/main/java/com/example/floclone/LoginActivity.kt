package com.example.floclone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.floclone.data.LoginRequest
import com.example.floclone.data.SongDatabase
import com.example.floclone.data.User
import com.example.floclone.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), LoginView {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSignUpTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.loginSignInBtn.setOnClickListener {
            login()
        }
    }

    private fun getLoginRequest(): LoginRequest{
        val email = binding.loginIdEt.text.toString() + "@" + binding.loginDirectInputEt.text.toString()
        val password = binding.loginPasswordEt.text.toString()

        return LoginRequest(email, password)
    }
    private fun login(){
        if(binding.loginIdEt.text.toString().isEmpty() || binding.loginDirectInputEt.text.toString().isEmpty()){
            Toast.makeText(this, "이메일을 입력해주세요",Toast.LENGTH_SHORT).show()
            return
        }

        if(binding.loginPasswordEt.text.toString().isEmpty()){
            Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val authService = AuthService()
        authService.setLoginView(this)

        authService.login(getLoginRequest())
    }

    private fun saveJwt(jwt: String){
        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putString("jwt",jwt)
        editor.apply()
    }

    private fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onLoginSuccess(accessToken: String, memberId: Int) {
        saveJwt(accessToken)
        Log.d("ACCESSTOKEN", accessToken.toString())
        Log.d("MEMBERID", memberId.toString())
        startMainActivity()
    }

    override fun onLoginFailure() {
        Log.d("LOGIN", "로그인 실패")
    }
}