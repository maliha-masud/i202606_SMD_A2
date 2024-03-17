package com.maliha.i202606

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.maliha.i202606.databinding.MainPgBinding

class MainPage : AppCompatActivity() {
    private lateinit var binding: MainPgBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.main_pg)
        supportActionBar?.hide()

        binding = MainPgBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        val currentUserUid = firebaseAuth.currentUser?.uid
        currentUserUid?.let {
            databaseReference.child("Users").child(it).child("name")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val userName = dataSnapshot.getValue(String::class.java)
//                        binding.nameTxt.text = userName
                        binding.nameTxt.text = userName?.split(" ")?.get(0) ?: "" // Retrieve the first name
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle errors
                    }
                })
        }

//        val notifsBtn = findViewById<ImageButton>(R.id.notifs_btn)
        binding.notifsBtn.setOnClickListener{
            startActivity(Intent(this, Notifications::class.java))
        }

//        val jcBtn = findViewById<TextView>(R.id.jc_txt)
        binding.jcTxt.setOnClickListener{
            startActivity(Intent(this, MentorPg::class.java))
        }

        //////////////////BOTTOM NAV BAR////////////////////
//        val addBtn = findViewById<ImageButton>(R.id.plus_btn)
        binding.plusBtn.setOnClickListener{
            startActivity(Intent(this, AddMentor::class.java))
        }

//        val searchBtn = findViewById<ImageButton>(R.id.search_btn)
        binding.searchBtn.setOnClickListener{
            startActivity(Intent(this, FindBar::class.java))
        }

//        val chatBtn = findViewById<ImageButton>(R.id.chat_btn)
        binding.chatBtn.setOnClickListener{
            startActivity(Intent(this, ChatBar::class.java))
        }

//        val profileBtn = findViewById<ImageButton>(R.id.profile_btn)
        binding.profileBtn.setOnClickListener{
            startActivity(Intent(this, MyProfile::class.java))
        }
    }
}