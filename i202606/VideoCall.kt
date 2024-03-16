package com.maliha.i202606

import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.TextView

class VideoCall : AppCompatActivity() {
    private lateinit var callTimeTextView: TextView
    private var startTime: Long = 0L
    private var elapsedTime: Long = 0L

    private val handler = Handler()

    private val timerRunnable = object : Runnable {
        override fun run() {
            elapsedTime = SystemClock.elapsedRealtime() - startTime
            updateTimerText()
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_call)
        supportActionBar?.hide()

        val crossBtn = findViewById<ImageButton>(R.id.close_btn)
        crossBtn.setOnClickListener { finish() }

        callTimeTextView = findViewById(R.id.call_time)
        startTime = SystemClock.elapsedRealtime()
        handler.postDelayed(timerRunnable, 0)
    }

    private fun updateTimerText() {
        val seconds = elapsedTime / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val formattedTime = String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60)
        callTimeTextView.text = formattedTime
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(timerRunnable)
    }
}
