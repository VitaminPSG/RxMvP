package com.android.rxmvp.presentation.resources

import android.content.Context
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat

class AndroidResourceManager(private val context: Context) : ResourceManager {

    override fun getInputStream(fileName: String) = context.resources.assets.open(fileName)

    override fun getString(@StringRes resId: Int) = context.getString(resId)

    override fun getString(@StringRes resId: Int, vararg formatArgs: Any) =
        context.getString(resId, *formatArgs)

    override fun getDimension(@DimenRes resId: Int) = context.resources.getDimension(resId)

    override fun getDimensionPixelSize(@DimenRes resId: Int) = context.resources.getDimensionPixelSize(resId)

    override fun getDrawable(@DrawableRes resId: Int) =
        ResourcesCompat.getDrawable(context.resources, resId, null)
}