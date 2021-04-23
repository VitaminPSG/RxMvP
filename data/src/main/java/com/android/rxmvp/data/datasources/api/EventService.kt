package com.android.rxmvp.data.datasources.api

import com.android.rxmvp.data.datasources.credentials.CredentialsProvider
import com.android.rxmvp.data.models.EventResponse
import com.android.rxmvp.data.models.SeatGeekEvent
import com.android.rxmvp.domain.models.Event
import com.android.rxmvp.domain.scheduler.SchedulerProvider
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface EventService {

    fun getEvents(): Single<List<Event>>
}

class EventServiceImpl(
    private val retrofit: Retrofit,
    private val schedulerProvider: SchedulerProvider,
    private val credentialsProvider: CredentialsProvider,
) : EventService {

    private val api: Api by lazy<Api>(LazyThreadSafetyMode.NONE) {
        retrofit.create(Api::class.java)
    }

    override fun getEvents(): Single<List<Event>> {
        return api.getEvents(credentialsProvider.clientId)
            .map { response -> response.events }
            .map { events -> events.map<SeatGeekEvent, Event> { event -> event } }
            .subscribeOn(schedulerProvider.io)
    }

    private interface Api {

        @GET("/2/events")
        fun getEvents(
            @Query("client_id") clientId: String,
            @Query("per_page") limit: Long = 25
        ): Single<EventResponse>

    }
}