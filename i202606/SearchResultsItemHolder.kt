import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.maliha.i202606.MentorPg
import com.maliha.i202606.R

class SearchResultsItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val sampleNameTextView: TextView = itemView.findViewById(R.id.sample_name)
    private val sampleDescTextView: TextView = itemView.findViewById(R.id.sample_desc)
    private val sampleAvailableTextView: TextView = itemView.findViewById(R.id.sample_available_txt)
    private val samplePriceTextView: TextView = itemView.findViewById(R.id.sample_price)
    private val sampleFavButton: ImageButton = itemView.findViewById(R.id.sample_fav)
    private val sample_available: RelativeLayout = itemView.findViewById(R.id.sample_available)
    private val context: Context = itemView.context

    fun bind(username: String, databaseReference: DatabaseReference) {
        // Retrieve mentor details from Firebase using username
        databaseReference.child("Mentors").child(username).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val uName = dataSnapshot.child("username").getValue(String::class.java)
                    val mentorName = dataSnapshot.child("name").getValue(String::class.java)
                    val mentorDesc = dataSnapshot.child("desc").getValue(String::class.java)
                    val mentorPrice = dataSnapshot.child("price").getValue(Int::class.java)
                    val mentorAvailable = dataSnapshot.child("status").getValue(String::class.java)
                    val mentorFav = dataSnapshot.child("fav").getValue(Boolean::class.java)

                    // Populate the search result item view with mentor data
                    sampleNameTextView.text = mentorName
                    sampleDescTextView.text = mentorDesc
                    samplePriceTextView.text = "$$mentorPrice/Session"
                    sampleAvailableTextView.text = mentorAvailable

                    // Set text color based on mentor status
                    if (mentorAvailable == "Available") {
                        sampleAvailableTextView.setTextColor(context.resources.getColor(R.color.available_color))
                        sample_available.setBackgroundResource(R.drawable.available_circle)
                    } else {
                        sampleAvailableTextView.setTextColor(context.resources.getColor(R.color.not_available_color))
                        sample_available.setBackgroundResource(R.drawable.not_available_circle)
                    }

                    // Set favorite button icon based on mentor fav status
                    if (mentorFav == true) {
                        sampleFavButton.setImageResource(R.drawable.filled_fav_heart)
                    } else {
                        sampleFavButton.setImageResource(R.drawable.not_filled_fav_heart)
                    }

                    // Set favorite button click listener
                    sampleFavButton.setOnClickListener {
                        // Toggle mentor's favorite status
                        val newFavStatus = !(mentorFav ?: false)

                        // Update favorite button icon
                        if (newFavStatus) {
                            sampleFavButton.setImageResource(R.drawable.filled_fav_heart)
                        } else {
                            sampleFavButton.setImageResource(R.drawable.not_filled_fav_heart)
                        }

                        // Update mentor's favorite status in the database
                        dataSnapshot.child("fav").ref.setValue(newFavStatus)
                    }

                    // Set click listener for mentor name text view
                    sampleNameTextView.setOnClickListener {
                        // Start MentorProfileActivity and pass the mentor's username as an extra
                        val intent = Intent(context, MentorPg::class.java)
                        intent.putExtra("username", uName)
                        context.startActivity(intent)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}
