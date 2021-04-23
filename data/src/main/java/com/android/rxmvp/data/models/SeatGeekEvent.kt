package com.android.rxmvp.data.models

import androidx.annotation.Keep
import com.android.rxmvp.data.datasources.database.Converters
import com.android.rxmvp.domain.models.Event
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@Keep
@JsonClass(generateAdapter = true)
data class SeatGeekEvent(
    @Json(name = "announce_date")
    val announceDate: String,
    @Json(name = "datetime_local")
    val datetimeLocal: String,
    @Json(name = "datetime_tbd")
    val datetimeTbd: Boolean,
    @Json(name = "datetime_utc")
    val datetimeUtc: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "id")
    override val id: Long,
    @Json(name = "title")
    val title: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "url")
    override val url: String,
) : Event {

    override val name: String
        get() = title

    override val datetime: Date
        get() = Converters().run { stringToDate(datetimeUtc) }
}
