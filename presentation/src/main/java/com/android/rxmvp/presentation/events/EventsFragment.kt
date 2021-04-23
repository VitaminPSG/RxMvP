package com.android.rxmvp.presentation.events

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.rxmvp.domain.models.FavoriteEvent
import com.android.rxmvp.presentation.R
import com.android.rxmvp.presentation.base.BaseFragment
import com.android.rxmvp.presentation.databinding.FragmentEventsBinding
import com.android.rxmvp.presentation.events.adapter.EventAdapter
import com.jakewharton.rxbinding4.swiperefreshlayout.refreshes
import io.reactivex.rxjava3.core.Observable
import org.koin.android.ext.android.inject

class EventsFragment : BaseFragment<EventsPresenter.View, EventsPresenter>(R.layout.fragment_events),
    EventsPresenter.View {

    override val presenter: EventsPresenter by inject()
    override val abstractView: EventsPresenter.View
        get() = this

    private val binding: FragmentEventsBinding by viewBinding(FragmentEventsBinding::bind)

    private val eventAdapter = EventAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvEvents.apply {
            adapter = eventAdapter
        }
    }

    override fun onNewEvents(events: List<FavoriteEvent>) {
        eventAdapter.update(events)
    }

    override fun setProgress(isVisible: Boolean) {
        binding.swipeToRefresh.isRefreshing = isVisible
    }

    override fun onClickFavorite(): Observable<FavoriteEvent> {
        return eventAdapter.onClickFavorite()
    }

    override fun onClickEvent(): Observable<FavoriteEvent> {
        return eventAdapter.onClickItem()
    }

    override fun onSwipeToRefresh(): Observable<Unit> {
        return binding.swipeToRefresh.refreshes()
    }
}