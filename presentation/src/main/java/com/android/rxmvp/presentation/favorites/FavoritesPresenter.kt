package com.android.rxmvp.presentation.favorites

import com.android.rxmvp.domain.models.FavoriteEvent
import com.android.rxmvp.domain.scheduler.SchedulerProvider
import com.android.rxmvp.domain.usecases.ChangeFavoriteUseCase
import com.android.rxmvp.domain.usecases.GetAllFavoritesEventsUseCase
import com.android.rxmvp.presentation.base.BasePresenter
import com.android.rxmvp.presentation.navigation.NavigationIntent
import com.android.rxmvp.presentation.navigation.Router
import io.reactivex.rxjava3.core.Observable
import timber.log.Timber

class FavoritesPresenter(
    private val getAllFavoritesEventsUseCase: GetAllFavoritesEventsUseCase,
    private val changeFavoriteUseCase: ChangeFavoriteUseCase,
    private val router: Router,
    private val schedulerProvider: SchedulerProvider,

    ) : BasePresenter<FavoritesPresenter.View>() {

    override fun onViewAttached(view: View) {
        super.onViewAttached(view)
        getAllFavoritesEventsUseCase()
            .observeOn(schedulerProvider.ui)
            .subscribe({
                view.onNewEvents(it)
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

        fun onClickFavorite(): Observable<FavoriteEvent>

        fun onClickEvent(): Observable<FavoriteEvent>
    }
}