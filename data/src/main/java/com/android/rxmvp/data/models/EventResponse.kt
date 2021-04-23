package com.android.rxmvp.data.models

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class EventResponse(
    @Json(name = "events")
    val events: List<SeatGeekEvent>,
    @Json(name = "meta")
    val meta: Meta,
)