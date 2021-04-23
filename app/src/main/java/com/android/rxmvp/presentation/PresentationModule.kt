package com.android.rxmvp.presentation

import com.android.rxmvp.presentation.bottom.BottomBarPresenter
import com.android.rxmvp.presentation.events.EventsPresenter
import com.android.rxmvp.presentation.favorites.FavoritesPresenter
import com.android.rxmvp.presentation.navigation.Router
import com.android.rxmvp.presentation.resources.AndroidResourceManager
import com.android.rxmvp.presentation.resources.ResourceManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val PresentationModule = module {
    single<ResourceManager> { AndroidResourceManager(context = androidContext()) }

    single { Router() }

    single {
        SinglePresenter(
            router = get(),
            schedulerProvider = get()
        )
    }

    single {
        EventsPresenter(
            getAllEventsUseCase = get(),
            changeFavoriteUseCase = get(),
            refreshEventsUseCase = get(),
            databaseIsOutdatedUseCase = get(),
            router = get(),
            schedulerProvider = get(),
        )
    }

    single {
        FavoritesPresenter(
            getAllFavoritesEventsUseCase = get(),
            changeFavoriteUseCase = get(),
            router = get(),
            schedulerProvider = get(),
        )
    }


    single {
        BottomBarPresenter(
            router = get(),
            schedulerProvider = get(),
        )
    }
}