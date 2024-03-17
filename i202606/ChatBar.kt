package com.maliha.i202606

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.ImageButton
import android.widget.TextView

class ChatBar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_bar)
        supportActionBar?.hide()

        val backBtn = findViewById<ImageButton>(R.id.back_btn)
        backBtn.setOnClickListener { finish() }

        val jcCommunityBtn = findViewById<ImageButton>(R.id.online_icon1)
        jcCommunityBtn.setOnClickListener {
            startActivity(Intent(this, CommunityChat::class.java))
        }

        val jcChat = findViewById<TextView>(R.id.jc_txt)
        jcChat.setOnClickListener{
            startActivity(Intent(this, MentorChat::class.java))
        }

        //BOTTOM NAV BAR
        val homeBtn = findViewById<ImageButton>(R.id.home_btn)
        homeBtn.setOnClickListener{
            startActivity(Intent(this, MainPage::class.java))
        }

        val searchBtn = findViewById<ImageButton>(R.id.search_btn)
        searchBtn.setOnClickListener{
            startActivity(Intent(this, FindBar::class.java))
        }

        val addBtn = findViewById<ImageButton>(R.id.plus_btn)
        addBtn.setOnClickListener{
            startActivity(Intent(this, AddMentor::class.java))
        }

        val profileBtn = findViewById<ImageButton>(R.id.profile_btn)
        profileBtn.setOnClickListener{
            startActivity(Intent(this, MyProfile::class.java))
        }
    }
}