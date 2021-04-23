package com.android.rxmvp.domain.usecases

import com.android.rxmvp.domain.models.SafeResult
import com.android.rxmvp.domain.repositories.EventRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import org.junit.Test

class RefreshEventsUseCaseTest {

    @Test
    fun `on error should return SafeResultError`() {
        val mockedEventRepository = mockk<EventRepository>(relaxed = true)

        val useCase = RefreshEventsUseCase(mockedEventRepository)

        every { mockedEventRepository.refresh() } returns Completable.error(Throwable())

        useCase().test()
            .assertValueCount(1)
            .assertValue {
                it is SafeResult.Error
            }

        verify(exactly = 1) {
            mockedEventRepository.refresh()
        }
    }

    @Test
    fun `on success should return SafeResultSuccess`() {
        val mockedEventRepository = mockk<EventRepository>(relaxed = true)

        val useCase = RefreshEventsUseCase(mockedEventRepository)

        every { mockedEventRepository.refresh() } returns Completable.complete()

        useCase().test()
            .assertValueCount(1)
            .assertValue {
                it is SafeResult.Success
            }

        verify(exactly = 1) {
            mockedEventRepository.refresh()
        }
    }
}