package com.android.rxmvp.domain.usecases

import com.android.rxmvp.domain.models.SafeResult
import com.android.rxmvp.domain.repositories.EventRepository
import io.reactivex.rxjava3.core.Single

class RefreshEventsUseCase(
    private val eventRepository: EventRepository
) {

    operator fun invoke(): Single<SafeResult<Long>> {
        return eventRepository.refresh()
            .andThen(Single.just(SafeResult.success(System.currentTimeMillis())))
            .onErrorResumeNext {
                Single.just(SafeResult.error(it))
            }
    }
}