package com.maliha.i202606

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.HorizontalScrollView
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.maliha.i202606.databinding.MainPgBinding

class MainPage : AppCompatActivity() {
    private lateinit var binding: MainPgBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.main_pg)
        supportActionBar?.hide()

        binding = MainPgBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        val currentUserUid = firebaseAuth.currentUser?.uid
        currentUserUid?.let {
            databaseReference.child("Users").child(it).child("name")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val userName = dataSnapshot.getValue(String::class.java)
//                        binding.nameTxt.text = userName
                        binding.nameTxt.text = userName?.split(" ")?.get(0) ?: "" // Retrieve the first name
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle errors
                    }
                })
        }

        val topMentorsLinearLayout: LinearLayout = binding.topMentorsLinearLayout
        val eduMentorsLinearLayout: LinearLayout = binding.eduMentorsLinearLayout
        val recentMentorsLinearLayout: LinearLayout = binding.recentMentorsLinearLayout
        databaseReference.child("Mentors").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val totalMentors = dataSnapshot.childrenCount.toInt()
                dataSnapshot.children.forEachIndexed { index, mentorSnapshot ->
                    val mentor = mentorSnapshot.getValue(Mentor::class.java)
                    if (mentor != null) {
                        // Inflate the mentor item layout with the appropriate parent linear layout
                        var parentLinearLayout = recentMentorsLinearLayout
                        if (mentor.type == "Top")
                            parentLinearLayout = topMentorsLinearLayout
                        else if (mentor.type == "Education")
                            parentLinearLayout = eduMentorsLinearLayout
                        val mentorView = layoutInflater.inflate(R.layout.mentor_item, parentLinearLayout, false)
                        val mentorViewHolder = MentorItemViewHolder(mentorView)
                        mentorViewHolder.bind(mentor, databaseReference)

                        // Populate the mentor view with mentor data
                        mentorView.findViewById<TextView>(R.id.mentor_name_txt).text = mentor.name
                        mentorView.findViewById<TextView>(R.id.mentor_desc).text = mentor.desc
                        mentorView.findViewById<TextView>(R.id.mentor_price).text = "$" + mentor.price.toString() + "/Session"

                        val mentorStatusTextView = mentorView.findViewById<TextView>(R.id.mentor_status_txt)
                        mentorStatusTextView.text = mentor.status
                        val mentorStatusRelativeLayout = mentorView.findViewById<RelativeLayout>(R.id.mentor_status)

//                        // Set text color based on mentor status
//                        if (mentor.status == "Available") {
//                            // Set color for Available status
//                            mentorStatusTextView.setTextColor(ContextCompat.getColor(this@MainPage, R.color.available_color))
//                            mentorStatusRelativeLayout.setBackgroundResource(R.drawable.available_circle)
//                        } else {
//                            // Set color for Not Available status
//                            mentorStatusTextView.setTextColor(ContextCompat.getColor(this@MainPage, R.color.not_available_color))
//                            mentorStatusRelativeLayout.setBackgroundResource(R.drawable.not_available_circle)
//                        }

//                        val favoriteButton = mentorView.findViewById<ImageButton>(R.id.fav_1)
//                        if (mentor.fav) {
//                            favoriteButton.setImageResource(R.drawable.filled_fav_heart)
//                        } else {
//                            favoriteButton.setImageResource(R.drawable.not_filled_fav_heart)
//                        }

                        // Add the mentor view to the parent linear layout
                        parentLinearLayout.addView(mentorView)

                        // Apply margin to the right of the mentor view if it's the last mentor
                        if (index == totalMentors - 1 || index == 4) {
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

//        val notifsBtn = findViewById<ImageButton>(R.id.notifs_btn)
        binding.notifsBtn.setOnClickListener{
            startActivity(Intent(this, Notifications::class.java))
        }

        //////////////////BOTTOM NAV BAR////////////////////
//        val addBtn = findViewById<ImageButton>(R.id.plus_btn)
        binding.plusBtn.setOnClickListener{
            startActivity(Intent(this, AddMentor::class.java))
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
}