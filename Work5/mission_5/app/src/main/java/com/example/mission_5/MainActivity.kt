package com.example.mission_5

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var memoEditText: EditText

    companion object {
        var savedMemo: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        memoEditText = findViewById(R.id.memoEditText)
        val nextButton = findViewById<Button>(R.id.nextButton)

        nextButton.setOnClickListener {
            val intent = Intent(this, ConfirmActivity::class.java)
            intent.putExtra("memo", memoEditText.text.toString())
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        savedMemo = memoEditText.text.toString()
    }

    override fun onResume() {
        super.onResume()
        savedMemo?.let {
            memoEditText.setText(it)
        }
    }

    override fun onRestart() {
        super.onRestart()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("다시 작성할까요?")
        builder.setMessage("이전에 작성한 내용을 다시 작성하시겠습니까?")
        builder.setPositiveButton("예") { _, _ -> /* 그대로 유지 */ }
        builder.setNegativeButton("아니요") { _, _ ->
            savedMemo = null
            memoEditText.setText("")
        }
        builder.show()
    }
}
