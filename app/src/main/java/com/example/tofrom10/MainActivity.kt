package com.example.tofrom10

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var difference: TextView
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var updateTimeRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        difference = findViewById(R.id.timeDifference)

        // Initialize the runnable for updating time
        updateTimeRunnable = object : Runnable {
            override fun run() {
                updateTime()
                // Schedule the runnable to run again after 1000 milliseconds (1 second)
                handler.postDelayed(this, 1000)
            }
        }

        // Start updating time
        handler.post(updateTimeRunnable)
    }

    override fun onDestroy() {
        // Remove the callbacks when the activity is destroyed to avoid memory leaks
        handler.removeCallbacks(updateTimeRunnable)
        super.onDestroy()
    }

    private fun updateTime() {
        val calendar: Calendar = Calendar.getInstance()
        val targetTimeMorning: Calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 10)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val targetTimeEvening: Calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 22) // 10 PM
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val timeDifferenceMorningMillis = targetTimeMorning.timeInMillis - calendar.timeInMillis
        val timeDifferenceEveningMillis = targetTimeEvening.timeInMillis - calendar.timeInMillis

        val minutesDifference: Long
        val displayText: String

        if (timeDifferenceMorningMillis > 0) {
            minutesDifference = timeDifferenceMorningMillis / (60 * 1000)
            displayText = "Minutes until 10 AM: $minutesDifference"
        } else {
            minutesDifference = timeDifferenceEveningMillis / (60 * 1000)
            displayText = "Minutes until 10 PM: $minutesDifference"
        }

        difference.text = displayText
    }
}
