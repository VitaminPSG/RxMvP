package com.android.rxmvp.domain.usecases

import com.android.rxmvp.domain.repositories.EventRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Observable
import org.junit.Test

class GetAllFavoritesEventsUseCaseTest {

    @Test
    fun `should call getAllFavoriteEvents`() {
        val mockedEventRepository = mockk<EventRepository>(relaxed = true)

        val useCase = GetAllFavoritesEventsUseCase(mockedEventRepository)

        every { mockedEventRepository.getAllFavoriteEvents() } returns Observable.just(emptyList())

        useCase().test()
            .assertValueCount(1)

        verify(exactly = 1) {
            mockedEventRepository.getAllFavoriteEvents()
        }
    }
}