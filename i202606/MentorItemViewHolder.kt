import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.maliha.i202606.MentorPg
import com.maliha.i202606.R

class MentorItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mentorNameTextView: TextView = itemView.findViewById(R.id.mentor_name_txt)
    private val mentorDescTextView: TextView = itemView.findViewById(R.id.mentor_desc)
    private val mentorStatusTextView: TextView = itemView.findViewById(R.id.mentor_status_txt)
    private val mentor_status: RelativeLayout = itemView.findViewById(R.id.mentor_status)
    private val mentorPriceTextView: TextView = itemView.findViewById(R.id.mentor_price)
    private val favoriteButton: ImageButton = itemView.findViewById(R.id.fav_1)
    private val context: Context = itemView.context

    fun bind(mentorId: String, databaseReference: DatabaseReference) {
        // Retrieve mentor details from Firebase using mentorId
        databaseReference.child("Mentors").child(mentorId).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val mentorName = dataSnapshot.child("name").getValue(String::class.java)
                    val mentorDesc = dataSnapshot.child("desc").getValue(String::class.java)
                    val mentorPrice = dataSnapshot.child("price").getValue(Int::class.java)
                    val mentorStatus = dataSnapshot.child("status").getValue(String::class.java)
                    val mentorFav = dataSnapshot.child("fav").getValue(Boolean::class.java)

                    // Populate the mentor view with mentor data
                    mentorNameTextView.text = mentorName
                    mentorDescTextView.text = mentorDesc
                    mentorPriceTextView.text = "$$mentorPrice/Session"
                    mentorStatusTextView.text = mentorStatus

                    // Set text color based on mentor status
                    if (mentorStatus == "Available") {
                        mentorStatusTextView.setTextColor(context.resources.getColor(R.color.available_color))
                        mentor_status.setBackgroundResource(R.drawable.available_circle)
                    } else {
                        mentorStatusTextView.setTextColor(context.resources.getColor(R.color.not_available_color))
                        mentor_status.setBackgroundResource(R.drawable.not_available_circle)
                    }

                    // Set favorite button icon based on mentor fav status
                    if (mentorFav == true) {
                        favoriteButton.setImageResource(R.drawable.filled_fav_heart)
                    } else {
                        favoriteButton.setImageResource(R.drawable.not_filled_fav_heart)
                    }

                    // Set favorite button click listener
                    favoriteButton.setOnClickListener {
                        // Toggle mentor's favorite status
                        val newFavStatus = !(mentorFav ?: false)

                        // Update favorite button icon
                        if (newFavStatus) {
                            favoriteButton.setImageResource(R.drawable.filled_fav_heart)
                        } else {
                            favoriteButton.setImageResource(R.drawable.not_filled_fav_heart)
                        }

                        // Update mentor's favorite status in the database
                        dataSnapshot.child("fav").ref.setValue(newFavStatus)
                    }

                    // Set click listener for mentor name text view
                    mentorNameTextView.setOnClickListener {
                        // Start MentorProfileActivity and pass the mentor's username as an extra
                        val intent = Intent(context, MentorPg::class.java)
                        intent.putExtra("username", mentorId)
                        context.startActivity(intent)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
            }
        })
    }
}
