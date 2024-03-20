package com.maliha.i202606

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.maliha.i202606.databinding.MentorReviewBinding

class MentorReview : AppCompatActivity() {
    private lateinit var binding: MentorReviewBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = MentorReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username") // Retrieve mentor's username from intent extras
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().reference

        databaseReference.child("Mentors").child(username.toString()).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val mentorName = dataSnapshot.child("name").getValue(String::class.java)

                mentorName?.let {
                    // Populate mentor details in the mentor_pg.xml layout
                    binding.nameTxt.text = mentorName

                    // Set mentor image based on name
                    when (mentorName) {
                        "Michael Drake" -> binding.mentorDp.setImageResource(R.drawable.man5)
                        "Emma Phillips", "Jane" -> binding.mentorDp.setImageResource(R.drawable.emma_icon)
                        "Alex" -> binding.mentorDp.setImageResource(R.drawable.jw_icon)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

        binding.backBtn.setOnClickListener { finish() }
        binding.communityBtn.setOnClickListener {
            startActivity(Intent(this, CommunityChat::class.java))
        }

        binding.submitBtn.setOnClickListener {
            val experience = binding.experienceEdittxt.text.toString()
            val rating = binding.rating.rating.toDouble()

            val reviewId = "review_${currentUserUid}_${username}"

            // Add review to the database
            val reviewData = hashMapOf(
                "uid" to currentUserUid,
                "mentorid" to username,
                "text" to experience,
                "stars" to rating
            )

            databaseReference.child("Reviews").child(reviewId).setValue(reviewData)
                .addOnSuccessListener {
                    // Review added successfully
                    Toast.makeText(this, "Review added successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    // Handle error
                    Toast.makeText(this, "Failed to add review", Toast.LENGTH_SHORT).show()
                }
        }
    }
}