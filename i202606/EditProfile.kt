package com.maliha.i202606

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.maliha.i202606.databinding.EditProfileBinding

class EditProfile : AppCompatActivity() {
    private lateinit var binding: EditProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    private var selectedCountry: String = ""
    private var selectedCity: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.edit_profile)
        supportActionBar?.hide()

        binding = EditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val backBtn = findViewById<ImageButton>(R.id.back_btn)
        binding.backBtn.setOnClickListener { finish() }

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        //Display the user's details in the fields
        val currentUserUid = firebaseAuth.currentUser?.uid
        currentUserUid?.let {
            databaseReference.child("Users").child(it).child("name")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val userName = dataSnapshot.getValue(String::class.java)
                        binding.nameEdittxt.setText(userName.toString())
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle errors
                    }
                })

            databaseReference.child("Users").child(it).child("email")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val email = dataSnapshot.getValue(String::class.java)
                        binding.emailEdittxt.setText(email.toString())
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle errors
                    }
                })

            databaseReference.child("Users").child(it).child("num")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val phoneNo = dataSnapshot.getValue(String::class.java)
                        binding.phoneNoEdittxt.setText(phoneNo.toString())
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle errors
                    }
                })

            databaseReference.child("Users").child(it).child("country")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val country = dataSnapshot.getValue(String::class.java)
                        binding.selectCountryTxt.text = country.toString()
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle errors
                    }
                })

            databaseReference.child("Users").child(it).child("city")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val city = dataSnapshot.getValue(String::class.java)
                        binding.selectCityTxt.text = city.toString()
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle errors
                    }
                })

            // Add click listener to the upload button
            binding.update.setOnClickListener {
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

//        val countryDropdown = findViewById<RelativeLayout>(R.id.country_dropdown)
        binding.countryDropdown.setOnClickListener { view ->
            showPopupMenuCountry(view)
        }

//        val cityDropdown = findViewById<RelativeLayout>(R.id.city_dropdown)
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
}