package com.example.study1

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

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

            replaceFragment(happy())
        }
        imageView1.setOnClickListener {

            replaceFragment(excitement())
        }
        imageView2.setOnClickListener {

            replaceFragment(soso())
        }
        imageView3.setOnClickListener {

            replaceFragment(nervous())
        }
        imageView4.setOnClickListener {

            replaceFragment(angry())
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
