package com.android.rxmvp.data.datasources.application

import androidx.lifecycle.Lifecycle.State
import androidx.lifecycle.LifecycleObserver
import io.reactivex.rxjava3.core.Observable

interface ApplicationLifecycle : LifecycleObserver {

    val appLifecycle: Observable<State>
}