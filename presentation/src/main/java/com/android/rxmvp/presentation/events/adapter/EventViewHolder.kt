package com.android.rxmvp.presentation.events.adapter

import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.android.rxmvp.domain.models.FavoriteEvent
import com.android.rxmvp.presentation.R
import com.android.rxmvp.presentation.databinding.EventItemBinding

class EventViewHolder(
    private val binding: EventItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onClickView(block: () -> Unit) {
        binding.root.setOnClickListener {
            block()
        }
    }

    fun onClickStar(block: () -> Unit) {
        binding.ivFavorite.setOnClickListener {
            block()
        }
    }

    fun bind(event: FavoriteEvent) {
        binding.apply {
            tvName.text = event.name
            tvDate.text = event.datetime.toString()
            ivFavorite.apply {
                @DrawableRes val drawableRes: Int = when (event.isFavorite) {
                    true -> R.drawable.ic_round_star_24
                    else -> R.drawable.ic_round_star_border_24
                }
                setImageResource(drawableRes)
            }
        }
    }
}