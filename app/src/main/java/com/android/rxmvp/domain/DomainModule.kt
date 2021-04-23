package com.android.rxmvp.domain

import com.android.rxmvp.data.scheduler.RxSchedulerProvider
import com.android.rxmvp.domain.scheduler.SchedulerProvider
import com.android.rxmvp.domain.usecases.ChangeFavoriteUseCase
import com.android.rxmvp.domain.usecases.DatabaseIsOutdatedUseCase
import com.android.rxmvp.domain.usecases.GetAllEventsUseCase
import com.android.rxmvp.domain.usecases.GetAllFavoritesEventsUseCase
import com.android.rxmvp.domain.usecases.RefreshEventsUseCase
import org.koin.dsl.module

val DomainModule = module {
    single<SchedulerProvider> { RxSchedulerProvider() }

    single {
        GetAllEventsUseCase(eventRepository = get())
    }

    single {
        GetAllFavoritesEventsUseCase(eventRepository = get())
    }

    single {
        ChangeFavoriteUseCase(eventRepository = get())
    }

    single {
        RefreshEventsUseCase(eventRepository = get())
    }

    single {
        DatabaseIsOutdatedUseCase(databaseRepository = get())
    }
}