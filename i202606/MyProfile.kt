package com.maliha.i202606

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.RelativeLayout

class MyProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_profile)
        supportActionBar?.hide()

        val backBtn = findViewById<ImageButton>(R.id.back_btn)
        backBtn.setOnClickListener { finish() }

        val profileEditBtn = findViewById<RelativeLayout>(R.id.profile_edit)
        profileEditBtn.setOnClickListener {
            startActivity(Intent(this, EditProfile::class.java))
        }

        val picEditBtn = findViewById<ImageButton>(R.id.edit_pfp)
        picEditBtn.setOnClickListener {
            startActivity(Intent(this, EditProfile::class.java))
        }

        val bookedSessionsBtn = findViewById<RelativeLayout>(R.id.bookedsessions_btn)
        bookedSessionsBtn.setOnClickListener {
            startActivity(Intent(this, BookedSessions::class.java))
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

        val searchBtn = findViewById<ImageButton>(R.id.search_btn)
        searchBtn.setOnClickListener{
            startActivity(Intent(this, FindBar::class.java))
        }

        val chatBtn = findViewById<ImageButton>(R.id.chat_btn)
        chatBtn.setOnClickListener{
            startActivity(Intent(this, ChatBar::class.java))
        }
    }
}