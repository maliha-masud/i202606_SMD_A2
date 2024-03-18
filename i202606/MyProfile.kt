package com.maliha.i202606

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.maliha.i202606.databinding.MyProfileBinding

class MyProfile : AppCompatActivity() {
    private lateinit var binding: MyProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.my_profile)
        supportActionBar?.hide()

        binding = MyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val backBtn = findViewById<ImageButton>(R.id.back_btn)
        binding.backBtn.setOnClickListener { finish() }

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        val currentUserUid = firebaseAuth.currentUser?.uid
        currentUserUid?.let {
            // Retrieve the user's name from Firebase Realtime Database
            databaseReference.child("Users").child(it).child("name")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val userName = dataSnapshot.getValue(String::class.java)
                        binding.nameTxt.text = userName
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle errors
                    }
                })

            databaseReference.child("Users").child(it).child("city")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val userCity = dataSnapshot.getValue(String::class.java)
                        binding.locTxt.text = userCity
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle errors
                    }
                })
        }

        val favMentorsLinearLayout: LinearLayout = binding.favMentorsLinearLayout
        databaseReference.child("Mentors").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEachIndexed { index, mentorSnapshot ->
                    val mentor = mentorSnapshot.getValue(Mentor::class.java)
                    if (mentor != null && mentor.fav) { // Filter only mentors with fav set to true
                        // Inflate the mentor item layout with the appropriate parent linear layout
                        val mentorView = layoutInflater.inflate(R.layout.mentor_item, favMentorsLinearLayout, false)
                        val mentorViewHolder = MentorItemViewHolder(mentorView)
                        mentorViewHolder.bind(mentor, databaseReference)

                        // Populate the mentor view with mentor data
                        mentorView.findViewById<TextView>(R.id.mentor_name_txt).text = mentor.name
                        mentorView.findViewById<TextView>(R.id.mentor_desc).text = mentor.desc
                        mentorView.findViewById<TextView>(R.id.mentor_price).text = ""

                        val mentorStatusTextView = mentorView.findViewById<TextView>(R.id.mentor_status_txt)
                        mentorStatusTextView.text = mentor.status

                        // Add the mentor view to the parent linear layout
                        favMentorsLinearLayout.addView(mentorView)

                        // Apply margin to the right of the mentor view if it's the last mentor
                        if (index.toLong() == dataSnapshot.childrenCount - 1) {
                            val params = mentorView.layoutParams as LinearLayout.LayoutParams
                            params.rightMargin = resources.getDimensionPixelSize(R.dimen.margin_end)
                            mentorView.layoutParams = params
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
            }
        })

//        val profileEditBtn = findViewById<RelativeLayout>(R.id.profile_edit)
        binding.profileEdit.setOnClickListener {
            startActivity(Intent(this, EditProfile::class.java))
        }

//        val picEditBtn = findViewById<ImageButton>(R.id.edit_pfp)
        binding.editPfp.setOnClickListener {
            startActivity(Intent(this, EditProfile::class.java))
        }

//        val bookedSessionsBtn = findViewById<RelativeLayout>(R.id.bookedsessions_btn)
        binding.bookedsessionsBtn.setOnClickListener {
            startActivity(Intent(this, BookedSessions::class.java))
        }

        ///////////////// Dropdown functionality /////////////////
        var menuBtn = binding.menuBtn
        menuBtn.setOnClickListener { showPopupMenu(menuBtn) }

        //////////////////BOTTOM NAV BAR////////////////////
//        val addBtn = findViewById<ImageButton>(R.id.plus_btn)
        binding.plusBtn.setOnClickListener{
            startActivity(Intent(this, AddMentor::class.java))
        }

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
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menu_options, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.edit_profile -> {
                    // Handle "Edit Profile" action
                    startActivity(Intent(this, EditProfile::class.java))
                    true
                }
                R.id.logout -> {
                    // Handle "Logout" action
                    logoutUser()
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

    private fun logoutUser() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
    }
}