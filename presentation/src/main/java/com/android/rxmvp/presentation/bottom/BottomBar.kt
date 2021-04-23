package com.android.rxmvp.presentation.bottom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.android.rxmvp.presentation.R
import com.android.rxmvp.presentation.base.ConstraintLayoutViewController
import com.android.rxmvp.presentation.databinding.BottomBarBinding
import com.android.rxmvp.presentation.navigation.NavigationIntent
import com.android.rxmvp.presentation.navigation.Screen
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.core.Observable

class BottomBar
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayoutViewController<BottomBarPresenter.View, BottomBarPresenter>(
    context,
    attrs,
    defStyleAttr
), BottomBarPresenter.View {

    private val binding: BottomBarBinding

    init {
        val view = View.inflate(context, R.layout.bottom_bar, this)
        binding = BottomBarBinding.bind(view)
    }

    override val presenter: BottomBarPresenter by viewInject()
    override val abstractView: BottomBarPresenter.View
        get() = this

    override fun setActiveScreen(screen: Screen) = binding.run {
        when (screen) {
            is NavigationIntent.OpenFavorites -> {
                tvFavorites.setActive(true)
                tvEvents.setActive(false)
            }
            is NavigationIntent.OpenEvents -> {
                tvFavorites.setActive(false)
                tvEvents.setActive(true)
            }
        }
    }

    override fun onClickEvents(): Observable<Unit> {
        return binding.tvEvents.clicks()
    }

    override fun onClickFavorite(): Observable<Unit> {
        return binding.tvFavorites.clicks()
    }

    private fun TextView.setActive(isActive: Boolean) {
        val color = ContextCompat.getColor(
            context, if (isActive) {
                R.color.green
            } else {
                R.color.gray
            }
        )
        setTextColor(color)
        compoundDrawables.filterNotNull()
            .forEach { drawable ->
                drawable.setTint(color)
            }
    }
}