package com.android.rxmvp.presentation

import com.android.rxmvp.domain.scheduler.SchedulerProvider
import com.android.rxmvp.presentation.navigation.NavigationIntent
import com.android.rxmvp.presentation.navigation.Router
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.Test

class SinglePresenterTest {

    private val testScheduler = TestScheduler()
    private val router = mockk<Router>(relaxed = true)

    private val schedulerProvider = mockk<SchedulerProvider>(relaxed = true).apply {
        every { ui } returns testScheduler
    }

    @Test
    fun `when screen is changed presenter should notify router`() {
        val mockView = mockk<SinglePresenter.View>(relaxed = true)
        val singlePresenter = SinglePresenter(
            router,
            schedulerProvider
        )

        every { mockView.onScreenChanged() } returns Observable.just(NavigationIntent.OpenEvents)

        singlePresenter.onViewAttached(mockView)
        testScheduler.triggerActions()

        verify(exactly = 1) {
            router.screen(NavigationIntent.OpenEvents)
        }
    }

    @Test
    fun `when new navigation intent is coming presenter should send it to view`() {
        val mockView = mockk<SinglePresenter.View>(relaxed = true)
        val singlePresenter = SinglePresenter(
            router,
            schedulerProvider
        )

        every { router.navigation } returns Observable.just(NavigationIntent.OpenEvents)

        singlePresenter.onViewWillShow(mockView)
        testScheduler.triggerActions()

        verify(exactly = 1) {
            mockView.navigateTo(NavigationIntent.OpenEvents)
        }
    }
}