package com.android.rxmvp.data.datasources.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.android.rxmvp.data.models.EventEntity
import com.android.rxmvp.data.models.FavoriteEventEntity
import com.android.rxmvp.data.models.FavoriteEventView
import com.android.rxmvp.data.models.VersionEntity
import com.android.rxmvp.domain.models.Event
import com.android.rxmvp.domain.models.FavoriteEvent
import com.android.rxmvp.domain.models.Version
import com.android.rxmvp.domain.scheduler.SchedulerProvider
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe

interface DatabaseService {

    fun addEvents(events: List<Event>): Completable

    fun getDatabaseVersion(): Maybe<Version>

    fun updateDatabaseVersion(): Completable

    fun getAllEvents(): Flowable<List<FavoriteEvent>>

    fun getAllFavoriteEvents(): Flowable<List<FavoriteEvent>>

    fun addFavorite(event: FavoriteEvent): Completable

    fun deleteFavorite(id: Long): Completable
}

class DatabaseServiceImpl(
    context: Context,
    private val schedulerProvider: SchedulerProvider,
) : DatabaseService {

    private val db: AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "events-database")
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
//                    Timber.tag("Room").d("Database is created")
                }

                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
//                    Timber.tag("Room").d("Database is opened")
                }
            })
            .build()

    private val eventsDao: EventsDao by lazy {
        db.eventsDao()
    }

    override fun addEvents(events: List<Event>): Completable {
        return eventsDao.addEvents(
            events = events.map { event ->
                EventEntity(event.id, event.name, event.datetime, event.url)
            }
        )
            .subscribeOn(schedulerProvider.io)
    }

    override fun getDatabaseVersion(): Maybe<Version> {
        return eventsDao.getLastVersion()
            .map<Version> { entity -> entity }
            .subscribeOn(schedulerProvider.io)
    }

    override fun updateDatabaseVersion(): Completable {
        return eventsDao.updateVersion(VersionEntity(lastUpdatedTime = System.currentTimeMillis()))
            .subscribeOn(schedulerProvider.io)
    }

    override fun getAllEvents(): Flowable<List<FavoriteEvent>> {
        return eventsDao.getAllEvents()
            .map { entities -> entities.map<FavoriteEventView, FavoriteEvent> { entity -> entity } }
            .subscribeOn(schedulerProvider.io)
    }

    override fun getAllFavoriteEvents(): Flowable<List<FavoriteEvent>> {
        return eventsDao.getAllFavoriteEvents()
            .map { entities -> entities.map<FavoriteEventEntity, FavoriteEvent> { entity -> entity } }
            .subscribeOn(schedulerProvider.io)
    }

    override fun addFavorite(event: FavoriteEvent): Completable {
        return eventsDao.addFavoriteEvent(
            FavoriteEventEntity(event.id, event.name, event.datetime, event.url)
        ).subscribeOn(schedulerProvider.io)
    }

    override fun deleteFavorite(id: Long): Completable {
        return eventsDao.deleteFavoriteEvent(id)
            .subscribeOn(schedulerProvider.io)
    }
}