package com.android.rxmvp.data.datasources.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.rxmvp.data.models.EventEntity
import com.android.rxmvp.data.models.FavoriteEventEntity
import com.android.rxmvp.data.models.FavoriteEventView
import com.android.rxmvp.data.models.VersionEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe

@Dao
interface EventsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateVersion(version: VersionEntity): Completable

    @Query("SELECT * FROM VersionEntity ORDER BY id DESC LIMIT 1")
    fun getLastVersion(): Maybe<VersionEntity>

    @Query("SELECT * FROM FavoriteEventView ORDER BY datetime")
    fun getAllEvents(): Flowable<List<FavoriteEventView>>

    @Query("SELECT * FROM FavoriteEventEntity ORDER BY datetime")
    fun getAllFavoriteEvents(): Flowable<List<FavoriteEventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEvents(events: List<EventEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavoriteEvent(event: FavoriteEventEntity): Completable

    @Query("DELETE FROM FavoriteEventEntity WHERE id = :id")
    fun deleteFavoriteEvent(id: Long): Completable
}