package com.maliha.i202606

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.ImageButton

class MentorChat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mentor_chat)
        supportActionBar?.hide()

        val backBtn = findViewById<ImageButton>(R.id.back_btn)
        backBtn.setOnClickListener { finish() }

        val voiceCallBtn = findViewById<ImageButton>(R.id.voice_call_btn)
        voiceCallBtn.setOnClickListener{
            startActivity(Intent(this, VoiceCall::class.java))
        }

        val vidCallBtn = findViewById<ImageButton>(R.id.vid_call_btn)
        vidCallBtn.setOnClickListener{
            startActivity(Intent(this, VoiceCall::class.java))
        }

        val picBtn = findViewById<ImageButton>(R.id.photo_btn)
        picBtn.setOnClickListener{
            startActivity(Intent(this, Photo::class.java))
        }

        val sendBtn = findViewById<ImageButton>(R.id.send_btn)
        sendBtn.setOnClickListener { finish() }

        //BOTTOM NAV BAR
        val addBtn = findViewById<ImageButton>(R.id.plus_btn)
        addBtn.setOnClickListener{
            startActivity(Intent(this, AddMentor::class.java))
        }

        val homeBtn = findViewById<ImageButton>(R.id.home_btn)
        homeBtn.setOnClickListener{
            startActivity(Intent(this, MainPage::class.java))
        }

        val searchBtn = findViewById<ImageButton>(R.id.search_btn)
        searchBtn.setOnClickListener{
            startActivity(Intent(this, FindBar::class.java))
        }

        val profileBtn = findViewById<ImageButton>(R.id.profile_btn)
        profileBtn.setOnClickListener{
            startActivity(Intent(this, MyProfile::class.java))
        }
    }
}