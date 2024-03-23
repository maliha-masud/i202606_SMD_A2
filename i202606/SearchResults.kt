package com.maliha.i202606

import SearchResultsItemHolder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.maliha.i202606.databinding.SearchResultsBinding

class SearchResults : AppCompatActivity() {
    private lateinit var binding: SearchResultsBinding
    private lateinit var searchResultsLinearLayout: LinearLayout
    private lateinit var searchText: String
    private var selectedFilter: String = ""
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.search_results)
        supportActionBar?.hide()

        binding = SearchResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val backBtn = findViewById<ImageButton>(R.id.back_btn)
        backBtn.setOnClickListener { finish() }

        val filterDropdown = findViewById<RelativeLayout>(R.id.filter_dropdown)
        filterDropdown.setOnClickListener { view ->
            showPopupMenuFilter(view, searchText)
        }

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

//        val currentUserUid = firebaseAuth.currentUser?.uid
//         = binding.searchResultsLinearLayout

        searchResultsLinearLayout = findViewById(R.id.search_results_linear_layout)
        searchText = intent.getStringExtra("searchText") ?: ""

        databaseReference = FirebaseDatabase.getInstance().reference
        searchMentorsByName(searchText)

//        searchResultsLinearLayout = findViewById(R.id.search_results_linear_layout)
//        val searchText = intent.getStringExtra("searchText") ?: ""
//
//        databaseReference = FirebaseDatabase.getInstance().reference
//        searchMentorsByName(searchText)

        //////////////////////////BOTTOM NAV BAR////////////////////////////
        val addBtn = findViewById<ImageButton>(R.id.plus_btn)
        addBtn.setOnClickListener {
            startActivity(Intent(this, AddMentor::class.java))
        }

        val homeBtn = findViewById<ImageButton>(R.id.home_btn)
        homeBtn.setOnClickListener {
            startActivity(Intent(this, MainPage::class.java))
        }

        val chatBtn = findViewById<ImageButton>(R.id.chat_btn)
        chatBtn.setOnClickListener {
            startActivity(Intent(this, ChatBar::class.java))
        }

        val profileBtn = findViewById<ImageButton>(R.id.profile_btn)
        profileBtn.setOnClickListener {
            startActivity(Intent(this, MyProfile::class.java))
        }
    }

    //////////////////////////////////////////////////////////
    private fun filterItemClick(menuItem: MenuItem) {
        val textView = findViewById<TextView>(R.id.filter_txt)
        textView.text = menuItem.title
        selectedFilter = menuItem.title.toString()
    }

    private fun searchMentorsByName(searchText: String) {
        databaseReference.child("Mentors").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach { mentorSnapshot ->
                    val userName = mentorSnapshot.child("username").getValue(String::class.java)
                    val mentorName = mentorSnapshot.child("name").getValue(String::class.java)
                    val mentorDesc = mentorSnapshot.child("desc").getValue(String::class.java)
                    val mentorPrice = mentorSnapshot.child("price").getValue(Int::class.java)
                    val mentorAvailable = mentorSnapshot.child("status").getValue(String::class.java)
                    val mentorFav = mentorSnapshot.child("fav").getValue(Boolean::class.java)

                    // Check if mentor name matches the search text
                    if (mentorName != null && mentorName.contains(searchText, ignoreCase = true)) {
                        // If it matches, display the mentor
                        displayMentor(mentorName, mentorDesc, mentorPrice, mentorAvailable, userName)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun applyFilter(selectedFilter: String, searchText: String) {
        // Clear the existing views in the linear layout
        searchResultsLinearLayout.removeAllViews()

        // Get the selected filter option and apply filter
        when (selectedFilter) {
            "Favourite" -> {
                // Display mentors matching the search text and filter by favourite
                databaseReference.child("Mentors")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            dataSnapshot.children.forEach { mentorSnapshot ->
                                val username = mentorSnapshot.child("username").getValue(String::class.java)
                                val mentorName = mentorSnapshot.child("name").getValue(String::class.java)
                                val mentorDesc = mentorSnapshot.child("desc").getValue(String::class.java)
                                val mentorPrice = mentorSnapshot.child("price").getValue(Int::class.java)
                                val mentorAvailable = mentorSnapshot.child("status").getValue(String::class.java)
                                val mentorFav = mentorSnapshot.child("fav").getValue(Boolean::class.java)

                                if (mentorFav == true && mentorName != null && mentorName.contains(searchText, ignoreCase = true)) {
                                    displayMentor(mentorName, mentorDesc, mentorPrice, mentorAvailable, username)
                                }
                            }
                        }
                        override fun onCancelled(databaseError: DatabaseError) {}
                    })
            }
            "Available" -> {
                // Display mentors matching the search text and filter by availability
                databaseReference.child("Mentors")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            dataSnapshot.children.forEach { mentorSnapshot ->
                                val username = mentorSnapshot.child("username").getValue(String::class.java)
                                val mentorName = mentorSnapshot.child("name").getValue(String::class.java)
                                val mentorDesc = mentorSnapshot.child("desc").getValue(String::class.java)
                                val mentorPrice = mentorSnapshot.child("price").getValue(Int::class.java)
                                val mentorAvailable = mentorSnapshot.child("status").getValue(String::class.java)

                                if (mentorAvailable == "Available" && mentorName != null && mentorName.contains(searchText, ignoreCase = true)) {
                                    displayMentor(mentorName, mentorDesc, mentorPrice, mentorAvailable, username)
                                }
                            }
                        }
                        override fun onCancelled(databaseError: DatabaseError) {}
                    })
            }
            else -> {
                // Display all mentors matching the search text without applying any filter
                searchMentorsByName(searchText)
            }
        }
    }


    private fun showPopupMenuFilter(view: View, searchText: String) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.search_filter, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            filterItemClick(menuItem)
            // Apply filter when a menu item is clicked
            applyFilter(menuItem.title.toString(), searchText)
            true
        }
        popupMenu.show()
    }

    private fun displayMentor(mentorName: String, mentorDesc: String?, mentorPrice: Int?, mentorAvailable: String?, username: String?) {
        // Inflate mentor item layout
        val searchView =
            layoutInflater.inflate(R.layout.search_result_item, searchResultsLinearLayout, false)

        // Bind data to mentor item
        searchView.findViewById<TextView>(R.id.sample_name).text = mentorName
        searchView.findViewById<TextView>(R.id.sample_desc).text = mentorDesc
        searchView.findViewById<TextView>(R.id.sample_price).text = "$$mentorPrice/Session"
        searchView.findViewById<TextView>(R.id.sample_available_txt).text = mentorAvailable

        // Bind data to mentor item using SearchResultsItemHolder
        val holder = SearchResultsItemHolder(searchView)
        holder.bind(username.toString(), databaseReference)

        // Add mentor item to searchResultsLinearLayout
        searchResultsLinearLayout.addView(searchView)
    }
}
