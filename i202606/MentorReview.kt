package com.maliha.i202606

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.maliha.i202606.databinding.MentorReviewBinding

class MentorReview : AppCompatActivity() {
    private lateinit var binding: MentorReviewBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = MentorReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username") // Retrieve mentor's username from intent extras
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().reference

  }