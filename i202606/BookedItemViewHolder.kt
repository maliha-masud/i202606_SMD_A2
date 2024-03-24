package com.maliha.i202606

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class BookedItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mentorNameTextView: TextView = itemView.findViewById(R.id.mentor_name)
    private val context: Context = itemView.context

    fun bind(mentorId: String, databaseReference: DatabaseReference) {
        // Retrieve mentor details from Firebase using mentorId
        databaseReference.child("Mentors").child(mentorId).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val mentorName = dataSnapshot.child("name").getValue(String::class.java)

                    // Populate the mentor view with mentor data
                    mentorNameTextView.text = mentorName

                    // Set click listener for mentor name text view
                    mentorNameTextView.setOnClickListener {
                        // Start MentorProfileActivity and pass the mentor's username as an extra
                        val intent = Intent(context, MentorPg::class.java)
                        intent.putExtra("username", mentorId)
                        context.startActivity(intent)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}
