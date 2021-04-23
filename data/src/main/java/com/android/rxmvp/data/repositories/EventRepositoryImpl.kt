package com.android.rxmvp.data.repositories

import com.android.rxmvp.data.datasources.api.EventService
import com.android.rxmvp.data.datasources.database.DatabaseService
import com.android.rxmvp.domain.models.FavoriteEvent
import com.android.rxmvp.domain.repositories.EventRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

class EventRepositoryImpl(
    private val eventService: EventService,
    private val databaseService: DatabaseService,
) : EventRepository {

    override fun refresh(): Completable {
        return eventService.getEvents()
            .flatMapCompletable { events -> databaseService.addEvents(events) }
            .andThen(databaseService.updateDatabaseVersion())
    }

    override fun getAllEvents(): Observable<List<FavoriteEvent>> {
        return databaseService.getAllEvents()
            .toObservable()
    }

    override fun getAllFavoriteEvents(): Observable<List<FavoriteEvent>> {
        return databaseService.getAllFavoriteEvents()
            .toObservable()
    }

    override fun addFavorite(event: FavoriteEvent): Completable {
        if (event.isFavorite) {
            return Completable.complete()
        }
        return databaseService.addFavorite(event)
    }

    override fun deleteFavorite(id: Long): Completable {
        return databaseService.deleteFavorite(id)
    }
}