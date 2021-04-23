package com.android.rxmvp.domain.models

import java.util.Date

interface Event {

    val id: Long
    val name: String
    val datetime: Date
    val url: String
}