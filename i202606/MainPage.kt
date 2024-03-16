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

class MainPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_pg)
        supportActionBar?.hide()

        val notifsBtn = findViewById<ImageButton>(R.id.notifs_btn)
        notifsBtn.setOnClickListener{
            startActivity(Intent(this, Notifications::class.java))
        }

        val jcBtn = findViewById<TextView>(R.id.jc_txt)
        jcBtn.setOnClickListener{
            startActivity(Intent(this, MentorPg::class.java))
        }

        //BOTTOM NAV BAR
        val addBtn = findViewById<ImageButton>(R.id.plus_btn)
        addBtn.setOnClickListener{
            startActivity(Intent(this, AddMentor::class.java))
        }

        val searchBtn = findViewById<ImageButton>(R.id.search_btn)
        searchBtn.setOnClickListener{
            startActivity(Intent(this, FindBar::class.java))
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