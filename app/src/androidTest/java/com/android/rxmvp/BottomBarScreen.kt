package com.android.rxmvp

import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView

class BottomBarScreen : Screen<BottomBarScreen>() {

    val tvEvents = KTextView { withId(R.id.tvEvents) }
    val tvFavorites = KTextView { withId(R.id.tvFavorites) }
}