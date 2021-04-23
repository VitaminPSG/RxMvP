package com.android.rxmvp.data.datasources.database

import org.junit.Assert
import org.junit.Test
import java.util.Calendar
import java.util.TimeZone

class ConvertersTest {

    private val converters = Converters()

    @Test
    fun `stringToDate should return Date`() {
        val expected = Calendar.getInstance()
            .apply {
                set(2020, 0, 30, 0, 0, 0)
                set(Calendar.MILLISECOND, 0)
                timeZone = TimeZone.getTimeZone("UTC")
            }
            .run { time }


        Assert.assertEquals(
            expected,
            converters.stringToDate("2020-01-30T00:00:00")
        )
    }

    @Test
    fun `dateToString should return String`() {
        val date = Calendar.getInstance()
            .apply {
                set(2020, 0, 30, 0, 0, 0)
                set(Calendar.MILLISECOND, 0)
                timeZone = TimeZone.getTimeZone("UTC")
            }
            .run { time }


        Assert.assertEquals(
            "2020-01-30T00:00:00",
            converters.dateToString(date)
        )
    }
}