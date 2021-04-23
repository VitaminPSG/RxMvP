package com.android.rxmvp.data.models

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.rxmvp.domain.models.Event
import java.util.Date

@Keep
@Entity
data class EventEntity(
    @PrimaryKey override val id: Long,
    @ColumnInfo(name = "name") override val name: String,
    @ColumnInfo(name = "datetime") override val datetime: Date,
    @ColumnInfo(name = "url") override val url: String,
) : Event