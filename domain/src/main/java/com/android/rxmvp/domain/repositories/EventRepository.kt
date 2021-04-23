package com.android.rxmvp.domain.repositories

import com.android.rxmvp.domain.models.FavoriteEvent
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface EventRepository {

    fun refresh(): Completable

    fun getAllEvents(): Observable<List<FavoriteEvent>>

    fun getAllFavoriteEvents(): Observable<List<FavoriteEvent>>

    fun addFavorite(event: FavoriteEvent): Completable

    fun deleteFavorite(id: Long): Completable
}