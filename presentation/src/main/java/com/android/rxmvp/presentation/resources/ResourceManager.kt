package com.android.rxmvp.presentation.resources

import android.graphics.drawable.Drawable
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import java.io.InputStream

interface ResourceManager {

    fun getInputStream(fileName: String): InputStream

    fun getString(@StringRes resId: Int): String

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String

    fun getDimension(@DimenRes resId: Int): Float

    fun getDimensionPixelSize(@DimenRes resId: Int): Int

    fun getDrawable(@DrawableRes resId: Int): Drawable?
}
