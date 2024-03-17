package com.maliha.i202606

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()

        Handler().postDelayed({
            checkUserAndNavigate()
        }, 5000)
    }

    private fun checkUserAndNavigate() {
        val currentUser = firebaseAuth.currentUser
        val intent = if (currentUser != null) {
            Intent(this, MainPage::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}
