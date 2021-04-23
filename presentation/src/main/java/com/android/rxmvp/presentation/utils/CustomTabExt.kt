package com.android.rxmvp.presentation.utils

import android.content.Context
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.android.rxmvp.presentation.R

fun CustomTabsIntent.Builder.createCustomTab(context: Context): CustomTabsIntent.Builder {
    val params = CustomTabColorSchemeParams.Builder()
        .setNavigationBarColor(ContextCompat.getColor(context, R.color.white))
        .setToolbarColor(ContextCompat.getColor(context, R.color.white))
        .setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.white))
        .build()

    return setDefaultColorSchemeParams(params)
//    setToolbarColor(ContextCompat.getColor(context, R.color.white))
        .setShowTitle(true)
        .setStartAnimations(
            context,
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )
        .setExitAnimations(
            context,
            R.anim.slide_in_left,
            R.anim.slide_out_right_fade
        )
}