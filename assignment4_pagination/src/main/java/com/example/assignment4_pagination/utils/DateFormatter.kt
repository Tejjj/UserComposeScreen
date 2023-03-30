package com.example.assignment4_pagination.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateFormatter {
    @RequiresApi(Build.VERSION_CODES.O)
    fun convertTimeStampToCalendarFormat(timeStamp: String): String {
        val inputTimeStampFormatter = DateTimeFormatter.ISO_DATE_TIME
        val calendarFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy")
        return LocalDateTime.parse(timeStamp, inputTimeStampFormatter).format(calendarFormatter)

    }
}