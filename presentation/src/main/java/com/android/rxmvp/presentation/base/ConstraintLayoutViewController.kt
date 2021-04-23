package com.android.rxmvp.presentation.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import org.koin.core.KoinComponent
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

abstract class ConstraintLayoutViewController<V, P : BasePresenter<V>>
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), KoinComponent {

    abstract val presenter: P
    abstract val abstractView: V

    private fun getOwner(): LifecycleOwner {
        return if (isInEditMode) {
            LifecycleOwner.register(MockBaseView())
        } else {
            LifecycleOwner.register(object : BaseView {
                override fun onViewAttached() {
                    presenter.onViewAttached(abstractView)
                }

                override fun onViewWillShow() {
                    presenter.onViewWillShow(abstractView)
                }

                override fun onViewWillHide() {
                    presenter.onViewWillHide()
                }

                override fun onViewDetached() {
                    presenter.onViewDetached()
                }
            })
        }
    }

    private val lifecycleOwner: LifecycleOwner by lazy {
        getOwner()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        lifecycleOwner.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        lifecycleOwner.onDetachedFromWindow()
        super.onDetachedFromWindow()
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        lifecycleOwner.onVisibilityChanged(isShown)
        super.onWindowVisibilityChanged(visibility)
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        lifecycleOwner.onVisibilityChanged(isShown)
        super.onVisibilityChanged(changedView, visibility)
    }

    protected inline fun <reified T> viewInject(
        qualifier: Qualifier? = null,
        noinline parameters: ParametersDefinition? = null
    ): Lazy<T> {
        return lazy(LazyThreadSafetyMode.NONE) { getKoin().get(qualifier, parameters) }
    }

}