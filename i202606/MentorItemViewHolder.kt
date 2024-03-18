package com.maliha.i202606

import android.content.Context
import android.view.View
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.firebase.database.DatabaseReference

class MentorItemViewHolder(itemView: View) {
    private val mentorNameTextView: TextView = itemView.findViewById(R.id.mentor_name_txt)
    private val mentorDescTextView: TextView = itemView.findViewById(R.id.mentor_desc)
    private val mentorStatusTextView: TextView = itemView.findViewById(R.id.mentor_status_txt)
    private val mentorStatus: RelativeLayout = itemView.findViewById(R.id.mentor_status)
    private val mentorPriceTextView: TextView = itemView.findViewById(R.id.mentor_price)
    private val favoriteButton: ImageButton = itemView.findViewById(R.id.fav_1)
    private val context: Context = itemView.context

    fun bind(mentor: Mentor, databaseReference: DatabaseReference) {
        mentorNameTextView.text = mentor.name
        mentorDescTextView.text = mentor.desc
        mentorPriceTextView.text = "$" + mentor.price.toString() + "/Session"
        mentorStatusTextView.text = mentor.status

        // Set text color based on mentor status
        if (mentor.status == "Available") {
            mentorStatusTextView.setTextColor(context.resources.getColor(R.color.available_color))
            mentorStatus.setBackgroundResource(R.drawable.available_circle)
        } else {
            mentorStatusTextView.setTextColor(context.resources.getColor(R.color.not_available_color))
            mentorStatus.setBackgroundResource(R.drawable.not_available_circle)
        }

        // Set favorite button icon based on mentor fav status
        if (mentor.fav) {
            favoriteButton.setImageResource(R.drawable.filled_fav_heart)
        } else {
            favoriteButton.setImageResource(R.drawable.not_filled_fav_heart)
        }

        // Set favorite button click listener
        favoriteButton.setOnClickListener {
            mentor.fav = !mentor.fav //toggle mentor's favorite status

            // Update favorite button icon
            if (mentor.fav) {
                favoriteButton.setImageResource(R.drawable.filled_fav_heart)
            } else {
                favoriteButton.setImageResource(R.drawable.not_filled_fav_heart)
            }

            // Update mentor's favorite status in the database
            databaseReference.child("Mentors").child(mentor.username).child("fav").setValue(mentor.fav)
        }
    }
}
