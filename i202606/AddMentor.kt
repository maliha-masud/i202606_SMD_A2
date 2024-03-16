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

class AddMentor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_mentor)
        supportActionBar?.hide()

        val backBtn = findViewById<ImageButton>(R.id.back_btn)
        backBtn.setOnClickListener { finish() }

        val upload_vid = findViewById<RelativeLayout>(R.id.video_upload)
        upload_vid.setOnClickListener{
            startActivity(Intent(this, Video::class.java))
        }

        val upload_pic = findViewById<RelativeLayout>(R.id.photo_upload)
        upload_pic.setOnClickListener{
            startActivity(Intent(this, Photo::class.java))
        }

        val availableDropdown = findViewById<RelativeLayout>(R.id.status_dropdown)
        availableDropdown.setOnClickListener { view ->
            showPopupMenuStatus(view)
        }

        val uploadBtn = findViewById<Button>(R.id.upload_btn)
        uploadBtn.setOnClickListener { finish() }

        //BOTTOM NAV BAR
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

        val profileBtn = findViewById<ImageButton>(R.id.profile_btn)
        profileBtn.setOnClickListener{
            startActivity(Intent(this, MyProfile::class.java))
        }
    }

    private fun showPopupMenuStatus(view: View) {
        val popupMenu = PopupMenu(this, view)

        popupMenu.menuInflater.inflate(R.menu.availabilities, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            statusItemClick(menuItem)
            true
        }
        popupMenu.show()
    }

    private fun statusItemClick(menuItem: MenuItem) {
        val textView = findViewById<TextView>(R.id.available_txt)
        textView.text = menuItem.title
    }
}