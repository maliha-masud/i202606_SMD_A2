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

class Photo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photo)
        supportActionBar?.hide()

        val crossBtn = findViewById<ImageButton>(R.id.cross_btn)
        crossBtn.setOnClickListener { finish() }

        val videoBtn = findViewById<TextView>(R.id.vid_txt)
        videoBtn.setOnClickListener{
            startActivity(Intent(this, Video::class.java))
        }

        val vidIconBtn = findViewById<ImageButton>(R.id.video_btn)
        vidIconBtn.setOnClickListener{
            startActivity(Intent(this, Video::class.java))
        }
    }
}