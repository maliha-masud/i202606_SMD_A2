package com.maliha.i202606

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.RelativeLayout

class EditProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile)
        supportActionBar?.hide()

        val backBtn = findViewById<ImageButton>(R.id.back_btn)
        backBtn.setOnClickListener { finish() }

        val uploadBtn = findViewById<Button>(R.id.update)
        uploadBtn.setOnClickListener{finish()}

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
    }
}