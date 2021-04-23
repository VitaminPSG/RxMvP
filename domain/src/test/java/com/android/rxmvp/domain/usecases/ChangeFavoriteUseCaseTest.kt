package com.android.rxmvp.domain.usecases

import com.android.rxmvp.domain.models.FavoriteEvent
import com.android.rxmvp.domain.repositories.EventRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class ChangeFavoriteUseCaseTest {

    @Test
    fun `user expects to remove favorite`() {
        val mockedEventRepository = mockk<EventRepository>(relaxed = true)
        val mockedEvent = mockk<FavoriteEvent>(relaxed = true)

        val useCase = ChangeFavoriteUseCase(mockedEventRepository)

        every { mockedEvent.isFavorite } returns true

        useCase(mockedEvent).test()
            .assertComplete()

        verify(exactly = 1) {
            mockedEventRepository.deleteFavorite(any())
        }

        verify(exactly = 0) {
            mockedEventRepository.addFavorite(any())
        }
    }

    @Test
    fun `user expects to add favorite`() {
        val mockedEventRepository = mockk<EventRepository>(relaxed = true)
        val mockedEvent = mockk<FavoriteEvent>(relaxed = true)

        val useCase = ChangeFavoriteUseCase(mockedEventRepository)

        every { mockedEvent.isFavorite } returns false

        useCase(mockedEvent).test()
            .assertComplete()

        verify(exactly = 0) {
            mockedEventRepository.deleteFavorite(any())
        }

        verify(exactly = 1) {
            mockedEventRepository.addFavorite(any())
        }
    }

}