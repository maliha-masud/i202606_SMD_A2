package com.maliha.i202606

import MentorItemViewHolder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.LinearLayout
import android.widget.TextView
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
                        binding.nameTxt.text = userName?.split(" ")?.get(0) ?: "" // Retrieve the first name
                    }

                    override fun onCancelled(databaseError: DatabaseError) {}
                })
        }

        val topMentorsLinearLayout: LinearLayout = binding.topMentorsLinearLayout
        val eduMentorsLinearLayout: LinearLayout = binding.eduMentorsLinearLayout
        val recentMentorsLinearLayout: LinearLayout = binding.recentMentorsLinearLayout
        databaseReference.child("Mentors").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val totalMentors = dataSnapshot.childrenCount.toInt()
                dataSnapshot.children.forEachIndexed { index, mentorSnapshot ->
                    val username = mentorSnapshot.child("username").getValue(String::class.java)
                    val mentorName = mentorSnapshot.child("name").getValue(String::class.java)
                    val mentorDesc = mentorSnapshot.child("desc").getValue(String::class.java)
                    val mentorPrice = mentorSnapshot.child("price").getValue(Int::class.java)
                    val mentorStatus = mentorSnapshot.child("status").getValue(String::class.java)
                    val mentorType = mentorSnapshot.child("type").getValue(String::class.java)

                    if (username != null && mentorName != null && mentorDesc != null && mentorPrice != null && mentorStatus != null && mentorType != null) {
                        // Inflate the mentor item layout with the appropriate parent linear layout
                        var parentLinearLayout = recentMentorsLinearLayout
                        if (mentorType == "Top")
                            parentLinearLayout = topMentorsLinearLayout
                        else if (mentorType == "Education")
                            parentLinearLayout = eduMentorsLinearLayout

                        val mentorView = layoutInflater.inflate(R.layout.mentor_item, parentLinearLayout, false)
                        val mentorViewHolder = MentorItemViewHolder(mentorView)

                        // Populate the mentor view with mentor data
                        mentorViewHolder.bind(username, databaseReference)

                        mentorView.findViewById<TextView>(R.id.mentor_name_txt).text = mentorName
                        mentorView.findViewById<TextView>(R.id.mentor_desc).text = mentorDesc
                        mentorView.findViewById<TextView>(R.id.mentor_price).text = "$" + mentorPrice.toString() + "/Session"
                        mentorView.findViewById<TextView>(R.id.mentor_status_txt).text = mentorStatus

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
            override fun onCancelled(databaseError: DatabaseError) {}
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