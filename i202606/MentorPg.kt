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

class MentorPg : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mentor_pg)
        supportActionBar?.hide()

        val backBtn = findViewById<ImageButton>(R.id.back_btn)
        backBtn.setOnClickListener { finish() }

        val reviewBtn = findViewById<RelativeLayout>(R.id.review_btn)
        reviewBtn.setOnClickListener{
            startActivity(Intent(this, MentorReview::class.java))
        }

        val communityBtn = findViewById<RelativeLayout>(R.id.community_btn)
        communityBtn.setOnClickListener{
            startActivity(Intent(this, CommunityChat::class.java))
        }

        val bookSessionBtn = findViewById<Button>(R.id.book_btn)
        bookSessionBtn.setOnClickListener{
            startActivity(Intent(this, BookMentor::class.java))
        }
    }
}