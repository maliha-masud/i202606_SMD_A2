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

class Video : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video)
        supportActionBar?.hide()

        val crossBtn = findViewById<ImageButton>(R.id.cross_btn)
        crossBtn.setOnClickListener { finish() }

        val photoBtn = findViewById<TextView>(R.id.photo_txt)
        photoBtn.setOnClickListener{
            startActivity(Intent(this, Photo::class.java))
        }

        val photoIconBtn = findViewById<ImageButton>(R.id.photo_btn)
        photoIconBtn.setOnClickListener{
            startActivity(Intent(this, Photo::class.java))
        }
    }
}