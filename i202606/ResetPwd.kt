package com.maliha.i202606

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageButton

class ResetPwd : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reset_pwd)
        supportActionBar?.hide()

        val backButton = findViewById<ImageButton>(R.id.back_btn)
        val loginBtn = findViewById<Button>(R.id.login_btn)
        val sendBtn = findViewById<Button>(R.id.send_btn)

        backButton.setOnClickListener { finish() }
        loginBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        sendBtn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
