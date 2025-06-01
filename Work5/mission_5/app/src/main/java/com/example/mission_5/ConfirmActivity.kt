package com.example.mission_5

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        val memo = intent.getStringExtra("memo")
        val memoTextView = findViewById<TextView>(R.id.memoTextView)
        memoTextView.text = memo
    }
}
