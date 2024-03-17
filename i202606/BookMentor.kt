package com.maliha.i202606

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import android.widget.ImageButton

class BookMentor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_mentor)
        supportActionBar?.hide()

        val backBtn = findViewById<ImageButton>(R.id.back_btn)
        backBtn.setOnClickListener { finish() }

        val bookBtn = findViewById<Button>(R.id.book_btn)
        bookBtn.setOnClickListener { finish() }

        val msgBtn = findViewById<ImageButton>(R.id.msg_btn)
        msgBtn.setOnClickListener{
            startActivity(Intent(this, MentorChat::class.java))
        }

        val phoneBtn = findViewById<ImageButton>(R.id.phonecall_btn)
        msgBtn.setOnClickListener{
            startActivity(Intent(this, VoiceCall::class.java))
        }

        val vidCallBtn = findViewById<ImageButton>(R.id.vidcall_btn)
        vidCallBtn.setOnClickListener{
            startActivity(Intent(this, VideoCall::class.java))
        }
    }
}