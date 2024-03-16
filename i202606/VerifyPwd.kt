package com.maliha.i202606

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.media.Image
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageButton

class VerifyPwd : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.verify_pwd)
        supportActionBar?.hide()

        val backButton = findViewById<ImageButton>(R.id.back_btn)
        backButton.setOnClickListener { finish() }

        var timer = findViewById<TextView>(R.id.time)
        val countdownTimer = object : CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                if(secondsRemaining / 10 < 1)
                    timer.text = "00:0" + "$secondsRemaining"
                else
                    timer.text = "00:" + "$secondsRemaining"
            }
            override fun onFinish() {
                timer.text = "00:00"
            }
        }

        countdownTimer.start()

        val verifyBtn = findViewById<Button>(R.id.verify_btn)
        verifyBtn.setOnClickListener {
            startActivity(
                Intent(this, LoginActivity::class.java));
        }
    }
}