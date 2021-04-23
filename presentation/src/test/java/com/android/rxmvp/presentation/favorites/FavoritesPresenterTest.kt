package com.android.rxmvp.presentation.favorites

import com.android.rxmvp.domain.models.FavoriteEvent
import com.android.rxmvp.domain.scheduler.SchedulerProvider
import com.android.rxmvp.domain.usecases.ChangeFavoriteUseCase
import com.android.rxmvp.domain.usecases.GetAllFavoritesEventsUseCase
import com.android.rxmvp.presentation.navigation.NavigationIntent
import com.android.rxmvp.presentation.navigation.Router
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.Test

class FavoritesPresenterTest {

    private val testScheduler = TestScheduler()
    private val getAllFavoritesEventsUseCase = mockk<GetAllFavoritesEventsUseCase>(relaxed = true)
    private val changeFavoriteUseCase = mockk<ChangeFavoriteUseCase>(relaxed = true)
    private val router = mockk<Router>(relaxed = true)

    private val schedulerProvider = mockk<SchedulerProvider>(relaxed = true).apply {
        every { ui } returns testScheduler
    }

    @Test
    fun `when user click on start should update favorite status`() {
        val mockView = mockk<FavoritesPresenter.View>(relaxed = true)
        val mockedEvent = mockk<FavoriteEvent>(relaxed = true)

        val favoritesPresenter = FavoritesPresenter(
            getAllFavoritesEventsUseCase,
            changeFavoriteUseCase,
            router,
            schedulerProvider
        )

        every { mockView.onClickFavorite() } returns Observable.just(mockedEvent)

        favoritesPresenter.onViewWillShow(view = mockView)

        testScheduler.triggerActions()

        verify(exactly = 1) {
            changeFavoriteUseCase.invoke(any())
        }
    }

    @Test
    fun `when user click on event should send OpenWeb intent to the router`() {
        val mockView = mockk<FavoritesPresenter.View>(relaxed = true)
        val mockedEvent = mockk<FavoriteEvent>(relaxed = true)

        val favoritesPresenter = FavoritesPresenter(
            getAllFavoritesEventsUseCase,
            changeFavoriteUseCase,
            router,
            schedulerProvider
        )

        every { mockedEvent.url } returns "url"
        every { mockView.onClickEvent() } returns Observable.just(mockedEvent)

        favoritesPresenter.onViewWillShow(view = mockView)

        testScheduler.triggerActions()

        verify(exactly = 1) {
            router.navigate(NavigationIntent.OpenWeb("url"))
        }
    }
}