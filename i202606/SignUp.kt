package com.maliha.i202606

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.RelativeLayout
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.maliha.i202606.databinding.SignUpBinding

class SignUp : AppCompatActivity() {
    private lateinit var binding:SignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private var selectedCountry: String = ""
    private var selectedCity: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.sign_up)
        supportActionBar?.hide()

        binding = SignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val loginBtn = binding.loginBtn //findViewById<Button>(R.id.login_btn)
        loginBtn.setOnClickListener { finish() }

//        val signUpBtn = findViewById<Button>(R.id.signup_btn)
//        signUpBtn.setOnClickListener{
//            startActivity(Intent(this, VerifyPwd::class.java))
//        }

        binding.signupBtn.setOnClickListener{
            val name = binding.nameEdittxt.text.toString()
            val email = binding.emailEdittxt.text.toString()
            val phone = binding.phoneNoEdittxt.text.toString()
            val country = this.selectedCountry
            val city = this.selectedCity
            val pwd = binding.pwdEdittxt.text.toString()

            //validation of fields
            if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() &&
                country.isNotEmpty() && city.isNotEmpty() && pwd.isNotEmpty()){
                firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener{
                    if (it.isSuccessful){
                        val user = firebaseAuth.currentUser
                        val userId = user?.uid
                        val database = FirebaseDatabase.getInstance().reference.child("Users")
                        userId?.let {
                            val userData = User(name, email, phone, country, city)
                            database.child(it).setValue(userData).addOnSuccessListener {
                                Toast.makeText(this, "Successfully signed up", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MyProfile::class.java))
                                finish() // Finish the current activity
                            }.addOnFailureListener { e ->
                                Toast.makeText(this, "Failed to save user data: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    else {
                        Toast.makeText(this, "Failed to sign up", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }

        /////////// Dropdown functionality ///////////////

        val countryDropdown = findViewById<RelativeLayout>(R.id.country_dropdown)

        countryDropdown.setOnClickListener { view ->
            showPopupMenuCountry(view)
        }

        val cityDropdown = findViewById<RelativeLayout>(R.id.city_dropdown)

        cityDropdown.setOnClickListener { view ->
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