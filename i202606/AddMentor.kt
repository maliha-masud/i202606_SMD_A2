package com.maliha.i202606

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.maliha.i202606.databinding.AddMentorBinding

class AddMentor : AppCompatActivity() {
    private lateinit var binding: AddMentorBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private var mentorStatus: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.add_mentor)
        supportActionBar?.hide()

        binding = AddMentorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.uploadBtn.setOnClickListener{
            val name = binding.nameEdittxt.text.toString()
            val desc = binding.descriptionEdittxt.text.toString()
            val status = this.mentorStatus
            // Unique username of mentor
            val username = generateUsername(name)

            // Validation of fields
            if (username.isNotEmpty() && name.isNotEmpty() && desc.isNotEmpty() && status.isNotEmpty()) {
                database = FirebaseDatabase.getInstance().getReference("Mentors")
                val mentor = Mentor(name, desc, 200, status, "Recent", false, username, 0.0)

                database.child(username).setValue(mentor).addOnSuccessListener {
                    Toast.makeText(this, "Mentor added successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainPage::class.java))
                }.addOnFailureListener{
                    Toast.makeText(this, "Failed to save mentor", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }

        /////////////// Buttons ///////////////
//        val backBtn = findViewById<ImageButton>(R.id.back_btn)
        binding.backBtn.setOnClickListener { finish() }

//        val upload_vid = findViewById<RelativeLayout>(R.id.video_upload)
        binding.videoUpload.setOnClickListener{
            startActivity(Intent(this, Video::class.java))
        }

//        val upload_pic = findViewById<RelativeLayout>(R.id.photo_upload)
        binding.photoUpload.setOnClickListener{
            startActivity(Intent(this, Photo::class.java))
        }

//        val availableDropdown = findViewById<RelativeLayout>(R.id.status_dropdown)
        binding.statusDropdown.setOnClickListener { view ->
            showPopupMenuStatus(view)
        }

//        val uploadBtn = findViewById<Button>(R.id.upload_btn)
//        binding.uploadBtn.setOnClickListener { finish() }

        /////////////////// BOTTOM NAV BAR ///////////////////
//        val homeBtn = findViewById<ImageButton>(R.id.home_btn)
        binding.homeBtn.setOnClickListener{
            startActivity(Intent(this, MainPage::class.java))
        }

//        val searchBtn = findViewById<ImageButton>(R.id.search_btn)
        binding.searchBtn.setOnClickListener{
            startActivity(Intent(this, FindBar::class.java))
        }

//        val chatBtn = findViewById<ImageButton>(R.id.chat_btn)
        binding.chatBtn.setOnClickListener{
            startActivity(Intent(this, ChatBar::class.java))
        }

//        val profileBtn = findViewById<ImageButton>(R.id.profile_btn)
        binding.profileBtn.setOnClickListener{
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
        binding.availableTxt.text = menuItem.title
        mentorStatus = menuItem.title.toString()
    }

    private fun generateUsername(name: String): String {
        val parts = name.trim().split(" ")
        val username = StringBuilder()

        for (part in parts) {
            username.append(part.toLowerCase())
        }

        return username.toString().trim()
    }
}