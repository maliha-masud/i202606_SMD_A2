package com.maliha.i202606

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageButton
import android.widget.Toast
import com.maliha.i202606.databinding.FindSearchBinding

class FindBar : AppCompatActivity() {
    private lateinit var binding: FindSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.find_search)
        supportActionBar?.hide()

        binding = FindSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }

//        val searchBtn = findViewById<ImageButton>(R.id.search_icon_btn)
//        searchBtn.setOnClickListener{
//            startActivity(Intent(this, SearchResults::class.java))
//        }

        binding.searchIconBtn.setOnClickListener {
            val searchText = binding.searchEdittxt.text.toString().trim()

            // Check if search text is not empty before navigating to SearchResults
            if (searchText.isNotEmpty()) {
                val intent = Intent(this@FindBar, SearchResults::class.java)
                intent.putExtra("searchText", searchText)
                startActivity(intent)
            }
            else
                Toast.makeText(this, "Please enter something to search", Toast.LENGTH_SHORT).show()
        }

        binding.searchEdittxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        //////////////////////// BOTTOM NAV BAR ////////////////////////
        binding.plusBtn.setOnClickListener{
            startActivity(Intent(this, AddMentor::class.java))
        }
        binding.homeBtn.setOnClickListener{
            startActivity(Intent(this, MainPage::class.java))
        }
        binding.chatBtn.setOnClickListener{
            startActivity(Intent(this, ChatBar::class.java))
        }
        binding.profileBtn.setOnClickListener{
            startActivity(Intent(this, MyProfile::class.java))
        }
    }
}