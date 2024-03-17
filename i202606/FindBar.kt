package com.maliha.i202606

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.ImageButton

class FindBar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.find_search)
        supportActionBar?.hide()

        val backBtn = findViewById<ImageButton>(R.id.back_btn)
        backBtn.setOnClickListener { finish() }

        val searchBtn = findViewById<ImageButton>(R.id.search_icon_btn)
        searchBtn.setOnClickListener{
            startActivity(Intent(this, SearchResults::class.java))
        }

        //BOTTOM NAV BAR
        val addBtn = findViewById<ImageButton>(R.id.plus_btn)
        addBtn.setOnClickListener{
            startActivity(Intent(this, AddMentor::class.java))
        }

        val homeBtn = findViewById<ImageButton>(R.id.home_btn)
        homeBtn.setOnClickListener{
            startActivity(Intent(this, MainPage::class.java))
        }

        val chatBtn = findViewById<ImageButton>(R.id.chat_btn)
        chatBtn.setOnClickListener{
            startActivity(Intent(this, ChatBar::class.java))
        }

        val profileBtn = findViewById<ImageButton>(R.id.profile_btn)
        profileBtn.setOnClickListener{
            startActivity(Intent(this, MyProfile::class.java))
        }
    }
}