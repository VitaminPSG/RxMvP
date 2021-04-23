package com.android.rxmvp.presentation.favorites

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.rxmvp.domain.models.FavoriteEvent
import com.android.rxmvp.presentation.R
import com.android.rxmvp.presentation.base.BaseFragment
import com.android.rxmvp.presentation.databinding.FragmentFavoritesBinding
import com.android.rxmvp.presentation.events.adapter.EventAdapter
import io.reactivex.rxjava3.core.Observable
import org.koin.android.ext.android.inject

class FavoritesFragment : BaseFragment<FavoritesPresenter.View, FavoritesPresenter>(R.layout.fragment_favorites),
    FavoritesPresenter.View {

    override val presenter: FavoritesPresenter by inject()
    override val abstractView: FavoritesPresenter.View
        get() = this

    private val binding: FragmentFavoritesBinding by viewBinding(FragmentFavoritesBinding::bind)

    private val eventAdapter = EventAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFavoriteEvents.apply {
            adapter = eventAdapter
        }
    }

    override fun onNewEvents(events: List<FavoriteEvent>) {
        eventAdapter.update(events)
    }

    override fun onClickFavorite(): Observable<FavoriteEvent> {
        return eventAdapter.onClickFavorite()
    }

    override fun onClickEvent(): Observable<FavoriteEvent> {
        return eventAdapter.onClickItem()
    }
}