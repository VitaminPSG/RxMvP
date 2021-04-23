package com.android.rxmvp.presentation.base

interface BaseView {

    fun onViewAttached()

    fun onViewWillShow()

    fun onViewWillHide()

    fun onViewDetached()
}