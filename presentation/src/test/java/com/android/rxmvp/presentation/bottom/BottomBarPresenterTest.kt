package com.android.rxmvp.presentation.bottom

import com.android.rxmvp.domain.scheduler.SchedulerProvider
import com.android.rxmvp.presentation.navigation.NavigationIntent
import com.android.rxmvp.presentation.navigation.Router
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.Test

class BottomBarPresenterTest {

    private val testScheduler = TestScheduler()
    private val router = mockk<Router>(relaxed = true)

    private val schedulerProvider = mockk<SchedulerProvider>(relaxed = true).apply {
        every { ui } returns testScheduler
    }

    @Test
    fun `on click events tab should send OpenEvents intent to router`() {
        val mockView = mockk<BottomBarPresenter.View>(relaxed = true)
        val bottomBarPresenter = BottomBarPresenter(
            router,
            schedulerProvider
        )

        every { mockView.onClickEvents() } returns Observable.just(Unit)

        bottomBarPresenter.onViewWillShow(mockView)
        testScheduler.triggerActions()

        verify(exactly = 1) {
            router.navigate(NavigationIntent.OpenEvents)
        }
    }

    @Test
    fun `on click favorite tab should send OpenFavorites intent to router`() {
        val mockView = mockk<BottomBarPresenter.View>(relaxed = true)
        val bottomBarPresenter = BottomBarPresenter(
            router,
            schedulerProvider
        )

        every { mockView.onClickFavorite() } returns Observable.just(Unit)

        bottomBarPresenter.onViewWillShow(mockView)
        testScheduler.triggerActions()

        verify(exactly = 1) {
            router.navigate(NavigationIntent.OpenFavorites)
        }
    }

    @Test
    fun `when screen is changed presenter should notify view`() {
        val mockView = mockk<BottomBarPresenter.View>(relaxed = true)
        val bottomBarPresenter = BottomBarPresenter(
            router,
            schedulerProvider
        )

        every { router.screen } returns Observable.just(NavigationIntent.OpenEvents)

        bottomBarPresenter.onViewWillShow(mockView)
        testScheduler.triggerActions()

        verify(exactly = 1) {
            mockView.setActiveScreen(NavigationIntent.OpenEvents)
        }
    }
}