package com.android.rxmvp.presentation.events

import com.android.rxmvp.domain.models.FavoriteEvent
import com.android.rxmvp.domain.models.SafeResult
import com.android.rxmvp.domain.scheduler.SchedulerProvider
import com.android.rxmvp.domain.usecases.ChangeFavoriteUseCase
import com.android.rxmvp.domain.usecases.DatabaseIsOutdatedUseCase
import com.android.rxmvp.domain.usecases.GetAllEventsUseCase
import com.android.rxmvp.domain.usecases.RefreshEventsUseCase
import com.android.rxmvp.presentation.navigation.NavigationIntent
import com.android.rxmvp.presentation.navigation.Router
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.Test

class EventsPresenterTest {

    private val testScheduler = TestScheduler()
    private val getAllEventsUseCase = mockk<GetAllEventsUseCase>(relaxed = true)
    private val changeFavoriteUseCase = mockk<ChangeFavoriteUseCase>(relaxed = true)
    private val refreshEventsUseCase = mockk<RefreshEventsUseCase>(relaxed = true)
    private val databaseIsOutdatedUseCase = mockk<DatabaseIsOutdatedUseCase>(relaxed = true)
    private val router = mockk<Router>(relaxed = true)

    private val schedulerProvider = mockk<SchedulerProvider>(relaxed = true).apply {
        every { ui } returns testScheduler
    }

    @Test
    fun `database is outdated should refresh database`() {
        val mockView = mockk<EventsPresenter.View>(relaxed = true)

        val eventsPresenter = EventsPresenter(
            getAllEventsUseCase,
            changeFavoriteUseCase,
            refreshEventsUseCase,
            databaseIsOutdatedUseCase,
            router,
            schedulerProvider
        )

        every { databaseIsOutdatedUseCase.invoke() } returns Single.just(true)

        eventsPresenter.onViewAttached(view = mockView)
        testScheduler.triggerActions()

        verify(exactly = 1) {
            refreshEventsUseCase.invoke()
        }

        verify(exactly = 1) {
            mockView.setProgress(true)
        }
    }

    @Test
    fun `database is up-to-date should not refresh`() {
        val mockView = mockk<EventsPresenter.View>(relaxed = true)

        val eventsPresenter = EventsPresenter(
            getAllEventsUseCase,
            changeFavoriteUseCase,
            refreshEventsUseCase,
            databaseIsOutdatedUseCase,
            router,
            schedulerProvider
        )

        every { databaseIsOutdatedUseCase.invoke() } returns Single.just(false)

        eventsPresenter.onViewAttached(view = mockView)
        testScheduler.triggerActions()

        verify(exactly = 0) {
            refreshEventsUseCase.invoke()
        }

        verify(exactly = 0) {
            mockView.setProgress(true)
        }
    }

    @Test
    fun `when new events comes should show it`() {
        val mockView = mockk<EventsPresenter.View>(relaxed = true)

        val eventsPresenter = EventsPresenter(
            getAllEventsUseCase,
            changeFavoriteUseCase,
            refreshEventsUseCase,
            databaseIsOutdatedUseCase,
            router,
            schedulerProvider
        )

        every { getAllEventsUseCase.invoke() } returns Observable.just(emptyList())

        eventsPresenter.onViewAttached(view = mockView)
        testScheduler.triggerActions()

        verify(exactly = 1) {
            mockView.onNewEvents(any())
        }
    }

    @Test
    fun `when no new events comes should not show it`() {
        val mockView = mockk<EventsPresenter.View>(relaxed = true)

        val eventsPresenter = EventsPresenter(
            getAllEventsUseCase,
            changeFavoriteUseCase,
            refreshEventsUseCase,
            databaseIsOutdatedUseCase,
            router,
            schedulerProvider
        )

        every { getAllEventsUseCase.invoke() } returns Observable.never()

        eventsPresenter.onViewAttached(view = mockView)
        testScheduler.triggerActions()

        verify(exactly = 0) {
            mockView.onNewEvents(any())
        }
    }

    @Test
    fun `when user swipes list app should refresh database`() {
        val mockView = mockk<EventsPresenter.View>(relaxed = true)

        val eventsPresenter = EventsPresenter(
            getAllEventsUseCase,
            changeFavoriteUseCase,
            refreshEventsUseCase,
            databaseIsOutdatedUseCase,
            router,
            schedulerProvider
        )

        every { refreshEventsUseCase.invoke() } returns Single.just(SafeResult.success(0))
        every { mockView.onSwipeToRefresh() } returns Observable.just(Unit)

        eventsPresenter.onViewAttached(view = mockView)

        testScheduler.triggerActions()

        verify(exactly = 1) {
            refreshEventsUseCase.invoke()
        }
    }

    @Test
    fun `when user click on start should update favorite status`() {
        val mockView = mockk<EventsPresenter.View>(relaxed = true)
        val mockedEvent = mockk<FavoriteEvent>(relaxed = true)

        val eventsPresenter = EventsPresenter(
            getAllEventsUseCase,
            changeFavoriteUseCase,
            refreshEventsUseCase,
            databaseIsOutdatedUseCase,
            router,
            schedulerProvider
        )

        every { mockView.onClickFavorite() } returns Observable.just(mockedEvent)

        eventsPresenter.onViewWillShow(view = mockView)

        testScheduler.triggerActions()

        verify(exactly = 1) {
            changeFavoriteUseCase.invoke(any())
        }
    }

    @Test
    fun `when user click on event should send OpenWeb intent to the router`() {
        val mockView = mockk<EventsPresenter.View>(relaxed = true)
        val mockedEvent = mockk<FavoriteEvent>(relaxed = true)

        val eventsPresenter = EventsPresenter(
            getAllEventsUseCase,
            changeFavoriteUseCase,
            refreshEventsUseCase,
            databaseIsOutdatedUseCase,
            router,
            schedulerProvider
        )

        every { mockedEvent.url } returns "url"
        every { mockView.onClickEvent() } returns Observable.just(mockedEvent)

        eventsPresenter.onViewWillShow(view = mockView)

        testScheduler.triggerActions()

        verify(exactly = 1) {
            router.navigate(NavigationIntent.OpenWeb("url"))
        }
    }
}