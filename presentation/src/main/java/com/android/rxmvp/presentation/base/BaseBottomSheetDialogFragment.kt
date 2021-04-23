package com.android.rxmvp.presentation.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<V, P : BasePresenter<V>>(@LayoutRes val contentLayoutId: Int) :
    BottomSheetDialogFragment() {

    abstract val presenter: P
    abstract val abstractView: V

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter.onViewAttached(abstractView)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onViewDetached()
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        presenter.onViewWillShow(abstractView)
    }

    @CallSuper
    override fun onStop() {
        super.onStop()
        presenter.onViewWillHide()
    }
}