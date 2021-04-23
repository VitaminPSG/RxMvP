package com.android.rxmvp.presentation.events.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.rxmvp.domain.models.FavoriteEvent
import com.android.rxmvp.presentation.databinding.EventItemBinding
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class EventAdapter : RecyclerView.Adapter<EventViewHolder>() {

    private val items: MutableList<FavoriteEvent> = ArrayList()

    private val favoriteClickSubject: PublishSubject<FavoriteEvent> = PublishSubject.create()
    private val itemClickSubject: PublishSubject<FavoriteEvent> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            EventItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val item = items[position]
        holder.onClickStar {
            favoriteClickSubject.onNext(item)
        }
        holder.onClickView {
            itemClickSubject.onNext(item)
        }
        holder.bind(item)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        itemClickSubject.onComplete()
        favoriteClickSubject.onComplete()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun update(dealerships: List<FavoriteEvent>) {
        this.items.clear()
        this.items.addAll(dealerships)
        this.notifyDataSetChanged()
    }

    fun onClickFavorite(): Observable<FavoriteEvent> {
        return favoriteClickSubject.hide()
    }

    fun onClickItem(): Observable<FavoriteEvent> {
        return itemClickSubject.hide()
    }

}