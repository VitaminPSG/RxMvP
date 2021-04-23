package com.android.rxmvp.presentation.navigation

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit

class Router {

    private val screenSubject = BehaviorSubject.create<Screen>()
    private val navigationSubject = PublishSubject.create<NavigationIntent>()

    val navigation: Observable<NavigationIntent> = navigationSubject.hide()
        .throttleFirst(VIEW_CLICKS_INTERVAL, TimeUnit.MILLISECONDS)

    val screen: Observable<Screen> = screenSubject

    fun navigate(intent: NavigationIntent) {
        Timber.tag("Router").d("On new navigation intent: $intent")
        if (intent == screenSubject.value) {
            return
        }
        navigationSubject.onNext(intent)
    }

    fun screen(screen: Screen) {
        Timber.tag("Router").d("On set new screen: $screen")
        screenSubject.onNext(screen)
    }

    private companion object {

        const val VIEW_CLICKS_INTERVAL = 1000L
    }
}
