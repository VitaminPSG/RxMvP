package com.android.rxmvp.data.models

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Embedded
import com.android.rxmvp.domain.models.FavoriteEvent
import java.util.Date

@Keep
@DatabaseView("SELECT *, (SELECT COUNT(FavoriteEventEntity.id) FROM FavoriteEventEntity WHERE EventEntity.id = FavoriteEventEntity.id) as is_favorite FROM EventEntity")
class FavoriteEventView(
    @Embedded
    val event: EventEntity,
    @ColumnInfo(name = "is_favorite") override val isFavorite: Boolean
) : FavoriteEvent {

    override val id: Long
        get() = event.id
    override val name: String
        get() = event.name
    override val datetime: Date
        get() = event.datetime
    override val url: String
        get() = event.url

}