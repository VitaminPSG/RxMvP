package com.android.rxmvp.domain.usecases

import com.android.rxmvp.domain.models.FavoriteEvent
import com.android.rxmvp.domain.repositories.EventRepository
import io.reactivex.rxjava3.core.Observable

class GetAllEventsUseCase(
    private val eventRepository: EventRepository
) {

    operator fun invoke(): Observable<List<FavoriteEvent>> {
        return eventRepository.getAllEvents()
    }
}