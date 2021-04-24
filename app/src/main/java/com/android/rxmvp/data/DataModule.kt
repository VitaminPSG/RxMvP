package com.android.rxmvp.data

import com.android.rxmvp.data.datasources.api.EventService
import com.android.rxmvp.data.datasources.api.EventServiceImpl
import com.android.rxmvp.data.datasources.application.AndroidApplicationLifecycle
import com.android.rxmvp.data.datasources.application.ApplicationLifecycle
import com.android.rxmvp.data.datasources.credentials.CredentialsProvider
import com.android.rxmvp.data.datasources.database.DatabaseService
import com.android.rxmvp.data.datasources.database.DatabaseServiceImpl
import com.android.rxmvp.data.repositories.DatabaseRepositoryImpl
import com.android.rxmvp.data.repositories.EventRepositoryImpl
import com.android.rxmvp.domain.repositories.DatabaseRepository
import com.android.rxmvp.domain.repositories.EventRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val DataModule = module {

    single<ApplicationLifecycle> {
        AndroidApplicationLifecycle()
    }

    single<CredentialsProvider> {
        object : CredentialsProvider {
            override val clientId: String
                get() = "MjE3MTU3MjV8MTYxODQxMDMzNS44NzY4MDQ4"
            override val baseUrl: String
                get() = "https://api.seatgeek.com"
        }
    }

    single {
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    single {
        val logger: HttpLoggingInterceptor = get()
        OkHttpClient()
            .newBuilder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(logger)
            .build()
    }

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        val provider: CredentialsProvider = get()
        val okHttpClient: OkHttpClient = get()
        val moshi: Moshi = get()

        Retrofit.Builder()
            .baseUrl(provider.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    single<EventService> {
        EventServiceImpl(
            retrofit = get(),
            schedulerProvider = get(),
            credentialsProvider = get(),
        )
    }

    single<DatabaseService> {
        DatabaseServiceImpl(
            context = androidApplication(),
            schedulerProvider = get(),
        )
    }

    single<EventRepository> {
        EventRepositoryImpl(
            eventService = get(),
            databaseService = get(),
        )
    }

    single<DatabaseRepository> {
        DatabaseRepositoryImpl(
            databaseService = get()
        )
    }
}