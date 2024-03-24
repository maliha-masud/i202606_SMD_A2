package com.maliha.i202606

import MentorItemViewHolder
import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.maliha.i202606.databinding.MyProfileBinding
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class MyProfile : AppCompatActivity() {
    private lateinit var binding: MyProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var userPfp: CircleImageView
    private lateinit var coverPic: ImageView

    private val pickProfileImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = result.data?.data
            imageUri?.let { uri ->
                uploadProfilePic(uri)
            }
        }
    }
    private val pickCoverImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = result.data?.data
            imageUri?.let { uri ->
                uploadCoverPic(uri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = MyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }
        userPfp = findViewById(R.id.user_pfp)
        coverPic = findViewById(R.id.cover_pic)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
        storageReference = FirebaseStorage.getInstance().reference

        val currentUserUid = firebaseAuth.currentUser?.uid
        currentUserUid?.let {
            // Retrieve the user's name from Firebase Realtime Database
            databaseReference.child("Users").child(it).child("name")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val userName = dataSnapshot.getValue(String::class.java)
                        binding.nameTxt.text = userName
                    }
                    override fun onCancelled(databaseError: DatabaseError) {}
                })

            databaseReference.child("Users").child(it).child("city")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val userCity = dataSnapshot.getValue(String::class.java)
                        binding.locTxt.text = userCity
                    }
                    override fun onCancelled(databaseError: DatabaseError) {}
                })
        }

        loadUserProfilePicture()
        loadUserCoverPicture()

        val favMentorsLinearLayout: LinearLayout = binding.favMentorsLinearLayout
        databaseReference.child("Mentors").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEachIndexed { index, mentorSnapshot ->
                    val mentorId = mentorSnapshot.key // Get mentor ID
                    val mentorName = mentorSnapshot.child("name").getValue(String::class.java)
                    val mentorDesc = mentorSnapshot.child("desc").getValue(String::class.java)
                    val mentorStatus = mentorSnapshot.child("status").getValue(String::class.java)
                    val mentorFav = mentorSnapshot.child("fav").getValue(Boolean::class.java)

                    if (mentorFav == true) { // Filter only mentors with fav set to true
                        // Inflate the mentor item layout with the appropriate parent linear layout
                        val mentorView = layoutInflater.inflate(R.layout.mentor_item, favMentorsLinearLayout, false)
                        val mentorViewHolder = MentorItemViewHolder(mentorView)
                        mentorViewHolder.bind(mentorId ?: "", databaseReference)

                        // Populate the mentor view with mentor data
                        mentorView.findViewById<TextView>(R.id.mentor_name_txt).text = mentorName
                        mentorView.findViewById<TextView>(R.id.mentor_desc).text = mentorDesc
                        mentorView.findViewById<TextView>(R.id.mentor_price).text = ""

                        val mentorStatusTextView = mentorView.findViewById<TextView>(R.id.mentor_status_txt)
                        mentorStatusTextView.text = mentorStatus

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
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        val reviewLinearLayout: LinearLayout = binding.reviewsLinearLayout
        databaseReference.child("Reviews").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEachIndexed { index, reviewSnapshot ->
                    val uid = reviewSnapshot.child("uid").getValue(String::class.java)
                    val mentorid = reviewSnapshot.child("mentorid").getValue(String::class.java)
                    val text = reviewSnapshot.child("text").getValue(String::class.java)
                    val stars = reviewSnapshot.child("stars").getValue(Double::class.java)

                    if (uid == currentUserUid) {
                        // Retrieve mentor's name based on mentor ID
                        databaseReference.child("Mentors").child(mentorid.toString())
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(mentorSnapshot: DataSnapshot) {
                                    val mentor = mentorSnapshot.child("name").getValue(String::class.java)
                                    // Inflate the review item layout with the parent linear layout
                                    val reviewView = layoutInflater.inflate(R.layout.mentor_review_item, reviewLinearLayout, false)

                                    // Populate the review view with review data
                                    reviewView.findViewById<TextView>(R.id.mentor_name).text = mentor
                                    reviewView.findViewById<TextView>(R.id.review_txt).text = text
                                    reviewView.findViewById<RatingBar>(R.id.ratingbar).rating = stars?.toFloat() ?: 0.0f

                                    reviewLinearLayout.addView(reviewView) //add the review view to the parent linear layout

                                    // Apply margin to the right of the review view if it's the last review
                                    if (index == dataSnapshot.childrenCount.toInt() - 1) {
                                        val params = reviewView.layoutParams as LinearLayout.LayoutParams
                                        params.rightMargin = resources.getDimensionPixelSize(R.dimen.margin_end)
                                        reviewView.layoutParams = params
                                    }
                                }
                                override fun onCancelled(databaseError: DatabaseError) {}
                            })
                        }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        binding.profileEdit.setOnClickListener {
            startActivity(Intent(this, EditProfile::class.java))
        }

        binding.editPfp.setOnClickListener {
            //startActivity(Intent(this, EditProfile::class.java))
            openImagePickerProfile() // Function to open image picker
        }

        binding.editCover.setOnClickListener {
            openImagePickerCover()
        }

        binding.bookedsessionsBtn.setOnClickListener {
            startActivity(Intent(this, BookedSessions::class.java))
        }

        ///////////////// Dropdown functionality /////////////////
        var menuBtn = binding.menuBtn
        menuBtn.setOnClickListener { showPopupMenu(menuBtn) }

        //////////////////BOTTOM NAV BAR////////////////////
        binding.plusBtn.setOnClickListener{
            startActivity(Intent(this, AddMentor::class.java))
        }
        binding.homeBtn.setOnClickListener{
            startActivity(Intent(this, MainPage::class.java))
        }
        binding.searchBtn.setOnClickListener{
            startActivity(Intent(this, FindBar::class.java))
        }
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

    private fun openImagePickerProfile() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickProfileImage.launch(intent)
    }
    private fun uploadProfilePic(imageUri: Uri) {
        val currentUserUid = firebaseAuth.currentUser?.uid
        storageReference = FirebaseStorage.getInstance().getReference("Users/$currentUserUid/profile_pic")
        storageReference.putFile(imageUri).addOnSuccessListener {
            loadUserProfilePicture()
            Toast.makeText(this@MyProfile, "Image upload successful", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this@MyProfile, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }
    private fun loadUserProfilePicture() {
        val currentUserUid = firebaseAuth.currentUser?.uid
        currentUserUid?.let { uid ->
            // Correctly set the storage reference to the user's profile picture
            val imageRef = FirebaseStorage.getInstance().getReference("Users/${uid}/profile_pic")

            // Get the download URL of the image
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                // Load the image using Picasso library into CircleImageView
                Picasso.get().load(uri.toString()).into(userPfp)
            }.addOnFailureListener { exception ->
                // If fetching the download URL fails, display an error message
                Toast.makeText(this@MyProfile, "Failed to load profile picture: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    /////////////////////////////////////////////////////////
    private fun openImagePickerCover() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickCoverImage.launch(intent)
    }
    private fun uploadCoverPic(imageUri: Uri) {
        val currentUserUid = firebaseAuth.currentUser?.uid
        val storageRef = FirebaseStorage.getInstance().getReference("Users/$currentUserUid/cover_photo")

        // Get dimensions of the uploaded image
        val inputStream = contentResolver.openInputStream(imageUri)
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeStream(inputStream, null, options)
        val imageWidth = options.outWidth

        // Check if the image meets the required width
        val screenWidth = resources.displayMetrics.widthPixels
        val requiredWidth = screenWidth // Adjust this value as needed

        if (imageWidth >= requiredWidth) {
            // Proceed with the upload
            storageRef.putFile(imageUri)
                .addOnSuccessListener {
                    loadUserCoverPicture()
                    Toast.makeText(this@MyProfile, "Cover photo upload successful", Toast.LENGTH_SHORT).show()
                    // Update the UI or perform any other actions
                }.addOnFailureListener{
                    Toast.makeText(this@MyProfile, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
        } else {
            // Display a message indicating that the image needs to be resized or cropped
            Toast.makeText(this@MyProfile, "Cover photo must be at least as wide as the screen", Toast.LENGTH_SHORT).show()
        }
    }
    private fun loadUserCoverPicture() {
        val currentUserUid = firebaseAuth.currentUser?.uid
        currentUserUid?.let { uid ->
            // Correctly set the storage reference to the user's profile picture
            val imageRef = FirebaseStorage.getInstance().getReference("Users/${uid}/cover_photo")

            // Get the download URL of the image
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                // Load the image using Picasso library into CircleImageView
                Picasso.get().load(uri.toString()).into(coverPic)
            }.addOnFailureListener { exception ->
                // If fetching the download URL fails, display an error message
                Toast.makeText(this@MyProfile, "Failed to load cover picture: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}