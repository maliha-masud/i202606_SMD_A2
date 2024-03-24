package com.maliha.i202606

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.maliha.i202606.databinding.EditProfileBinding
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class EditProfile : AppCompatActivity() {
    private lateinit var binding: EditProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    private var selectedCountry: String = ""
    private var selectedCity: String = ""

    private lateinit var storageReference: StorageReference
    private lateinit var userPfp: CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = EditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        val currentUserUid = firebaseAuth.currentUser?.uid

        userPfp = findViewById(R.id.user_pfp)
        storageReference = FirebaseStorage.getInstance().reference
        loadUserProfilePicture()

        currentUserUid?.let { //Display the user's details in the fields
            databaseReference.child("Users").child(it).child("name")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val userName = dataSnapshot.getValue(String::class.java)
                        binding.nameEdittxt.setText(userName.toString())
                    }
                    override fun onCancelled(databaseError: DatabaseError) {}
                })

            databaseReference.child("Users").child(it).child("email")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val email = dataSnapshot.getValue(String::class.java)
                        binding.emailEdittxt.setText(email.toString())
                    }
                    override fun onCancelled(databaseError: DatabaseError) {}
                })

            databaseReference.child("Users").child(it).child("num")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val phoneNo = dataSnapshot.getValue(String::class.java)
                        binding.phoneNoEdittxt.setText(phoneNo.toString())
                    }
                    override fun onCancelled(databaseError: DatabaseError) {}
                })

            databaseReference.child("Users").child(it).child("country")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val country = dataSnapshot.getValue(String::class.java)
                        binding.selectCountryTxt.text = country.toString()
                    }
                    override fun onCancelled(databaseError: DatabaseError) {}
                })

            databaseReference.child("Users").child(it).child("city")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val city = dataSnapshot.getValue(String::class.java)
                        binding.selectCityTxt.text = city.toString()
                    }
                    override fun onCancelled(databaseError: DatabaseError) {}
                })

            binding.update.setOnClickListener { // Add click listener to the upload button
                val newName = binding.nameEdittxt.text.toString()
                val newEmail = binding.emailEdittxt.text.toString()
                val newPhoneNo = binding.phoneNoEdittxt.text.toString()
                val newCountry = binding.selectCountryTxt.text.toString()
                val newCity = binding.selectCityTxt.text.toString()

                // Update user's fields in the database under the current user's UID
                databaseReference.child("Users").child(currentUserUid).apply {
                    child("name").setValue(newName)
                    child("email").setValue(newEmail)
                    child("num").setValue(newPhoneNo)
                    child("country").setValue(newCountry)
                    child("city").setValue(newCity)
                }
                startActivity(Intent(this, MyProfile::class.java))
            }
        }

        binding.countryDropdown.setOnClickListener { view ->
            showPopupMenuCountry(view)
        }

        binding.cityDropdown.setOnClickListener { view ->
            showPopupMenuCity(view)
        }
    }

    private fun showPopupMenuCountry(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.countries, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            countryItemClick(menuItem)
            true
        }
        popupMenu.show()
    }
    private fun countryItemClick(menuItem: MenuItem) {
        val textView = findViewById<TextView>(R.id.select_country_txt)
        textView.text = menuItem.title
        selectedCountry = menuItem.title.toString()
    }

    private fun showPopupMenuCity(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.cities, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            cityItemClick(menuItem)
            true
        }
        popupMenu.show()
    }
    private fun cityItemClick(menuItem: MenuItem) {
        val textView = findViewById<TextView>(R.id.select_city_txt)
        textView.text = menuItem.title
        selectedCity = menuItem.title.toString()
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
                Toast.makeText(this@EditProfile, "Failed to load profile picture: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}