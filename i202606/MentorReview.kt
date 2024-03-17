package com.maliha.i202606

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import android.widget.ImageButton

class MentorReview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mentor_review)
        supportActionBar?.hide()

        val backBtn = findViewById<ImageButton>(R.id.back_btn)
        backBtn.setOnClickListener { finish() }

        val commBtn = findViewById<ImageButton>(R.id.community_btn)
        commBtn.setOnClickListener {
            startActivity(Intent(this, CommunityChat::class.java))
        }

        val submitBtn = findViewById<Button>(R.id.submit_btn)
        submitBtn.setOnClickListener { finish() }
    }
}