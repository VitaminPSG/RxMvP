package com.android.rxmvp.presentation.bottom

import com.android.rxmvp.domain.scheduler.SchedulerProvider
import com.android.rxmvp.presentation.base.BasePresenter
import com.android.rxmvp.presentation.navigation.NavigationIntent
import com.android.rxmvp.presentation.navigation.Router
import com.android.rxmvp.presentation.navigation.Screen
import io.reactivex.rxjava3.core.Observable
import timber.log.Timber

class BottomBarPresenter(
    private val router: Router,
    private val schedulerProvider: SchedulerProvider,
) : BasePresenter<BottomBarPresenter.View>() {

    override fun onViewWillShow(view: View) {
        super.onViewWillShow(view)

        view.onClickEvents()
            .observeOn(schedulerProvider.ui)
            .subscribe({
                router.navigate(NavigationIntent.OpenEvents)
            }, {
                Timber.e("On click events causes an exception $it")
            })

        view.onClickFavorite()
            .observeOn(schedulerProvider.ui)
            .subscribe({
                router.navigate(NavigationIntent.OpenFavorites)
            }, {
                Timber.e("On click favorite causes an exception $it")
            })

        router.screen
            .observeOn(schedulerProvider.ui)
            .subscribe({
                view.setActiveScreen(screen = it)
            }, {
                Timber.e("Set screen causes an exception $it")
            })
    }

    interface View {

        fun setActiveScreen(screen: Screen)
        fun onClickEvents(): Observable<Unit>
        fun onClickFavorite(): Observable<Unit>
    }
}