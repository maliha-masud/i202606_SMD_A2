package com.maliha.i202606

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageButton

class ForgotPwd : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_pwd)
        supportActionBar?.hide()

        val backButton = findViewById<ImageButton>(R.id.back_btn)
        val loginBtn = findViewById<Button>(R.id.login_btn)

        backButton.setOnClickListener { finish() }
        loginBtn.setOnClickListener { finish() }

        val sendBtn = findViewById<Button>(R.id.send_btn)

        sendBtn.setOnClickListener {
            startActivity(
                Intent(this, ResetPwd::class.java));
        }
    }
}
