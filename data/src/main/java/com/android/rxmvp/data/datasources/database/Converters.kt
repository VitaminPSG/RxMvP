package com.android.rxmvp.data.datasources.database

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class Converters {

    @TypeConverter
    fun stringToDate(date: String): Date {
        return formatter.parse(date)
    }

    @TypeConverter
    fun dateToString(date: Date): String {
        return formatter.format(date)
    }

    private companion object {

        private val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
            .apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
    }
}