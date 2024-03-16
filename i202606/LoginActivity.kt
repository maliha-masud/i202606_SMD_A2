package com.maliha.i202606

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_pg)
        supportActionBar?.hide()

        var forgotbtn = findViewById<Button>(R.id.forgot_pwd_btn)

        forgotbtn.setOnClickListener {
            startActivity(
                Intent(this, ForgotPwd::class.java));
        }

        var loginBtn = findViewById<Button>(R.id.login_btn)
        loginBtn.setOnClickListener {
            startActivity(
                Intent(this, MainPage::class.java));
        }

        var signup = findViewById<Button>(R.id.signup_btn)

        signup.setOnClickListener{
            startActivity(Intent(this, SignUp::class.java))
        }
    }
}
