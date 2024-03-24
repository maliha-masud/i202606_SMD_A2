package com.maliha.i202606

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.google.firebase.database.*
import com.maliha.i202606.databinding.MentorPgBinding

class MentorPg : AppCompatActivity() {
    private lateinit var binding: MentorPgBinding
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.mentor_pg)
        supportActionBar?.hide()
        binding = MentorPgBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().reference

        val username = intent.getStringExtra("username") // Retrieve mentor's username from intent extras
        databaseReference.child("Mentors").child(username.toString()).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val mentorName = dataSnapshot.child("name").getValue(String::class.java)
                val mentorPos = dataSnapshot.child("desc").getValue(String::class.java)
                val mentorStars = dataSnapshot.child("rating").getValue(Double::class.java)

                if (mentorName != null) { // Check if mentor name is not null
                    // Populate mentor details in the mentor_pg.xml layout
                    binding.nameTxt.text = mentorName
                    binding.mentorPos.text = mentorPos
                    binding.mentorRating.text = mentorStars.toString()

                    val about = "I am a passionate $mentorPos with a focus on creating user-centric and intuitive interfaces. With 10 years of experience, I have had the opportunity to work on diverse projects that have shaped my understanding of design principles and user experience."
                    binding.aboutMeTxt.text = about

                    if(mentorName != null) {
                        if (mentorName == "Michael Drake")
                            binding.mentorDp.setImageResource(R.drawable.man5)
                        else if (mentorName =="Emma Phillips" || mentorName =="Jane")
                            binding.mentorDp.setImageResource(R.drawable.emma_icon)
                        else if (mentorName =="Alex")
                            binding.mentorDp.setImageResource(R.drawable.jw_icon)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        binding.backBtn.setOnClickListener { finish() }

        binding.reviewBtn.setOnClickListener{
            val intent = Intent(this, MentorReview::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        binding.communityBtn.setOnClickListener{
            startActivity(Intent(this, CommunityChat::class.java))
        }

        binding.bookBtn.setOnClickListener{
            startActivity(Intent(this, BookMentor::class.java))
        }
    }
}