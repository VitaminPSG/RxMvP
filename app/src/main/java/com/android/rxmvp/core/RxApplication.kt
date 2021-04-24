package com.android.rxmvp.core

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.android.rxmvp.data.DataModule
import com.android.rxmvp.data.datasources.application.ApplicationLifecycle
import com.android.rxmvp.domain.DomainModule
import com.android.rxmvp.presentation.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class RxApplication : Application(), LifecycleObserver {

    private val startupModules = listOf(
        PresentationModule,
        DomainModule,
        DataModule,
    )

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger()
            androidContext(this@RxApplication)
            koin.loadModules(startupModules)
            koin.createRootScope()
        }.apply {
            val applicationLifecycleObserver: ApplicationLifecycle = koin.get()
            ProcessLifecycleOwner.get().lifecycle.addObserver(applicationLifecycleObserver)
        }
    }
}