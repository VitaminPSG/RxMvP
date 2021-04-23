package com.android.rxmvp.presentation.events

import com.android.rxmvp.domain.models.FavoriteEvent
import com.android.rxmvp.domain.scheduler.SchedulerProvider
import com.android.rxmvp.domain.usecases.ChangeFavoriteUseCase
import com.android.rxmvp.domain.usecases.DatabaseIsOutdatedUseCase
import com.android.rxmvp.domain.usecases.GetAllEventsUseCase
import com.android.rxmvp.domain.usecases.RefreshEventsUseCase
import com.android.rxmvp.presentation.base.BasePresenter
import com.android.rxmvp.presentation.navigation.NavigationIntent
import com.android.rxmvp.presentation.navigation.Router
import io.reactivex.rxjava3.core.Observable
import timber.log.Timber

class EventsPresenter(
    private val getAllEventsUseCase: GetAllEventsUseCase,
    private val changeFavoriteUseCase: ChangeFavoriteUseCase,
    private val refreshEventsUseCase: RefreshEventsUseCase,
    private val databaseIsOutdatedUseCase: DatabaseIsOutdatedUseCase,
    private val router: Router,
    private val schedulerProvider: SchedulerProvider,
) : BasePresenter<EventsPresenter.View>() {

    override fun onViewAttached(view: View) {
        super.onViewAttached(view)
        databaseIsOutdatedUseCase()
            .filter { it == true }
            .observeOn(schedulerProvider.ui)
            .doOnSuccess { view.setProgress(true) }
            .flatMapSingle { refreshEventsUseCase() }
            .observeOn(schedulerProvider.ui)
            .subscribe({
                view.setProgress(false)
                Timber.d("Database is synchronized: $it")
            }, {
                view.setProgress(false)
                Timber.e("Can't get events $it")
            })
            .disposeOnViewDetached()

        getAllEventsUseCase()
            .observeOn(schedulerProvider.ui)
            .subscribe({
                view.onNewEvents(it)
            }, {
                Timber.e("Can't get events $it")
            })
            .disposeOnViewDetached()

        view.onSwipeToRefresh()
            .flatMapSingle { refreshEventsUseCase() }
            .observeOn(schedulerProvider.ui)
            .subscribe({
                view.setProgress(false)
                Timber.d("On complete: $it")
            }, {
                Timber.e("Can't get favorites")
            })
            .disposeOnViewDetached()
    }

    override fun onViewWillShow(view: View) {
        super.onViewWillShow(view)
        view.onClickFavorite()
            .flatMapCompletable { event -> changeFavoriteUseCase(event) }
            .observeOn(schedulerProvider.ui)
            .subscribe({
                Timber.d("On Complete")
            }, {
                Timber.e("On click: error: $it")
            })
            .disposeOnViewWillHide()

        view.onClickEvent()
            .observeOn(schedulerProvider.ui)
            .subscribe({
                router.navigate(NavigationIntent.OpenWeb(it.url))
            }, {
                Timber.e("On click: error: $it")
            })
            .disposeOnViewWillHide()
    }

    interface View {

        fun onNewEvents(events: List<FavoriteEvent>)
        fun setProgress(isVisible: Boolean)

        fun onClickFavorite(): Observable<FavoriteEvent>
        fun onClickEvent(): Observable<FavoriteEvent>
        fun onSwipeToRefresh(): Observable<Unit>
    }
}