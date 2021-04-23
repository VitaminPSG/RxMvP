package com.android.rxmvp.presentation

import com.android.rxmvp.domain.scheduler.SchedulerProvider
import com.android.rxmvp.presentation.base.BasePresenter
import com.android.rxmvp.presentation.navigation.NavigationIntent
import com.android.rxmvp.presentation.navigation.Router
import com.android.rxmvp.presentation.navigation.Screen
import io.reactivex.rxjava3.core.Observable
import timber.log.Timber

class SinglePresenter(
    private val router: Router,
    private val schedulerProvider: SchedulerProvider
) : BasePresenter<SinglePresenter.View>() {

    override fun onViewWillShow(view: View) {
        super.onViewWillShow(view)
        router.navigation
            .observeOn(schedulerProvider.ui)
            .subscribe({
                view.navigateTo(intent = it)
            }, {
                Timber.e(it, "Can't navigate to screen")
            })
            .disposeOnViewWillHide()
    }

    override fun onViewAttached(view: View) {
        super.onViewAttached(view)
        view.onScreenChanged()
            .observeOn(schedulerProvider.ui)
            .subscribe({
               router.screen(it)
            }, {
                Timber.e(it, "Can't navigate to screen")
            })
            .disposeOnViewDetached()
    }

    interface View {

        fun onScreenChanged(): Observable<Screen>

        fun navigateTo(intent: NavigationIntent)
    }
}