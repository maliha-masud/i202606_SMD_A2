package com.maliha.i202606

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.*

class BookedSessions : AppCompatActivity() {
    private lateinit var bookedMentorsLinearLayout: LinearLayout
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.booked_sessions)
        supportActionBar?.hide()

        val backBtn = findViewById<ImageButton>(R.id.back_btn)
        backBtn.setOnClickListener { finish() }

        bookedMentorsLinearLayout = findViewById(R.id.booked_mentors_linear_layout)
        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        // Retrieve the current user's UID
        val currentUserUid = auth.currentUser?.uid

        // Query the booked sessions for the current user
        if (currentUserUid != null) {
            databaseReference.child("Sessions").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.children.forEach { sessionSnapshot ->
                        val sessionUid = sessionSnapshot.child("uid").getValue(String::class.java)
                        val mentorId = sessionSnapshot.child("mentorid").getValue(String::class.java)
                        val sessionDate = sessionSnapshot.child("date").getValue(String::class.java)
                        val sessionTime = sessionSnapshot.child("time").getValue(String::class.java)

                        if (sessionUid == currentUserUid) {
                            // Create a layout inflater
                            val inflater = LayoutInflater.from(this@BookedSessions)

                            // Inflate the mentor session layout
                            val sessionLayout = inflater.inflate(R.layout.booked_session_item, null)

                            // Populate the session details
                            val mentorNameTextView = sessionLayout.findViewById<TextView>(R.id.mentor_name)
                            val mentorDescTextView = sessionLayout.findViewById<TextView>(R.id.mentor_desc)
                            val mentorDateTextView = sessionLayout.findViewById<TextView>(R.id.mentor_date)
                            val mentorTimeTextView = sessionLayout.findViewById<TextView>(R.id.mentor_time)

                            // Fetch mentor details from Firebase using mentorId
                            databaseReference.child("Mentors").child(mentorId.toString()).addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(mentorSnapshot: DataSnapshot) {
                                    val mentorName = mentorSnapshot.child("name").getValue(String::class.java)
                                    val mentorDesc = mentorSnapshot.child("desc").getValue(String::class.java)

                                    mentorNameTextView.text = mentorName
                                    mentorDescTextView.text = mentorDesc
                                }
                                override fun onCancelled(error: DatabaseError) {}
                            })
                            mentorDateTextView.text = sessionDate
                            mentorTimeTextView.text = sessionTime

                            val viewHolder = BookedItemViewHolder(sessionLayout) // Create BookedItemViewHolder
                            viewHolder.bind(mentorId.toString(), databaseReference) // Bind session data to BookedItemViewHolder
                            // Add the session layout to the linear layout
                            bookedMentorsLinearLayout.addView(sessionLayout)
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
        }
    }
}