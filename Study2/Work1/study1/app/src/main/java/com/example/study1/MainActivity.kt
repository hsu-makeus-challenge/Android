package com.example.study1

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView0: ImageView = findViewById(R.id.imageView0)
        val imageView1: ImageView = findViewById(R.id.imageView1)
        val imageView2: ImageView = findViewById(R.id.imageView2)
        val imageView3: ImageView = findViewById(R.id.imageView3)
        val imageView4: ImageView = findViewById(R.id.imageView4)


        imageView0.setOnClickListener {

            replaceFragment(fragment1())
        }
        imageView1.setOnClickListener {

            replaceFragment(fragment2())
        }
        imageView2.setOnClickListener {

            replaceFragment(fragment3())
        }
        imageView3.setOnClickListener {

            replaceFragment(fragment4())
        }
        imageView4.setOnClickListener {

            replaceFragment(fragment5())
        }
    }

    // Fragment 전환 메서드
    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}

