package com.android.rxmvp.data.datasources.application

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.State
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class AndroidApplicationLifecycle : ApplicationLifecycle {

    private val applicationLifecycleObserver = BehaviorSubject.createDefault(State.INITIALIZED)

    override val appLifecycle: Observable<State> = applicationLifecycleObserver

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onAppCreated() {
        applicationLifecycleObserver.onNext(State.CREATED)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppStarted() {
        applicationLifecycleObserver.onNext(State.STARTED)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onAppResumed() {
        applicationLifecycleObserver.onNext(State.RESUMED)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onAppPaused() {
        applicationLifecycleObserver.onNext(State.STARTED)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppStopped() {
        applicationLifecycleObserver.onNext(State.CREATED)
    }
}