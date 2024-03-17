package com.maliha.i202606

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.ImageButton
import android.widget.TextView

class BookedSessions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.booked_sessions)
        supportActionBar?.hide()

        val backBtn = findViewById<ImageButton>(R.id.back_btn)
        backBtn.setOnClickListener { finish() }

        val jcBtn = findViewById<TextView>(R.id.jc_name)
        jcBtn.setOnClickListener{
            startActivity(Intent(this, MentorPg::class.java))
        }
    }
}