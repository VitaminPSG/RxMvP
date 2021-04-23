package com.android.rxmvp.domain.usecases

import com.android.rxmvp.domain.models.FavoriteEvent
import com.android.rxmvp.domain.repositories.EventRepository
import io.reactivex.rxjava3.core.Completable

class ChangeFavoriteUseCase(
    private val eventRepository: EventRepository
) {

    operator fun invoke(event: FavoriteEvent): Completable {
        return if (event.isFavorite) {
            eventRepository.deleteFavorite(event.id)
        } else {
            eventRepository.addFavorite(event)
        }
    }
}