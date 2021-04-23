package com.android.rxmvp.data.datasources.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.rxmvp.data.models.EventEntity
import com.android.rxmvp.data.models.FavoriteEventEntity
import com.android.rxmvp.data.models.FavoriteEventView
import com.android.rxmvp.data.models.VersionEntity

@Database(
    entities = [EventEntity::class, FavoriteEventEntity::class, VersionEntity::class],
    views = [FavoriteEventView::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun eventsDao(): EventsDao
}