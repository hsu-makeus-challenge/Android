package com.example

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.umc1week.R
import com.example.umc1week.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding //바인딩 변수 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //화면을 상태바/내비게이션바까지 확장해서 표시
        setContentView(R.layout.activity_main)
        //화면에 표시할 XML파일 설정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


//        val happy : ImageView = findViewById(R.id.imageView2)
//        //이미지 뷰를 코드에 사용할 수 있도록 연결
//        happy.setOnClickListener{  //이미지를 눌렀을때 이벤트
//            val intent = Intent(this, Happy::class.java)
//            //현대 Activity에서 Happy클래스 화면으로 이동
//            startActivity(intent)
//            //위의 intent 설정을 실행 => 화면 전환 실행
//        }
//
//        val excitement : ImageView = findViewById(R.id.imageView3)
//        excitement.setOnClickListener{
//            val intent = Intent(this, Excitement::class.java)
//            startActivity(intent)
//        }
//
//        val soso : ImageView = findViewById(R.id.imageView4)
//        soso.setOnClickListener{
//            val intent = Intent(this, Soso::class.java)
//            startActivity(intent)
//        }
//
//        val melancholy : ImageView = findViewById(R.id.imageView5)
//        melancholy.setOnClickListener{
//            val intent = Intent(this, Melancholy::class.java)
//            startActivity(intent)
//        }
//
//        val angry : ImageView = findViewById(R.id.imageView6)
//        angry.setOnClickListener{
//            val intent = Intent(this, Angry::class.java)
//            startActivity(intent)
//        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        //XML 파일을 코드에서 쓸 수 있게 연결하는 시작 코드
        //ActivityMainBinding : ViewBinding이 자동으로 만들어주는 클래스 이름
        //.inflate(layoutInflater) : XML 파일을 메모리에 띄우는 작업, 화면으로 생성
        setContentView(binding.root)

        //위와 차이점 : findViewById로 하나하나씩 찾지 않아도 괜찮다.
        binding.imageView2.setOnClickListener {
            val intent = Intent(this, Happy::class.java)
            startActivity(intent)
        }

        binding.imageView3.setOnClickListener {
            val intent = Intent(this, Excitement::class.java)
            startActivity(intent)
        }

        binding.imageView4.setOnClickListener {
            val intent = Intent(this, Soso::class.java)
            startActivity(intent)
        }

        binding.imageView5.setOnClickListener {
            val intent = Intent(this, Melancholy::class.java)
            startActivity(intent)
        }

        binding.imageView6.setOnClickListener {
            val intent = Intent(this, Angry::class.java)
            startActivity(intent)
        }
    }
}