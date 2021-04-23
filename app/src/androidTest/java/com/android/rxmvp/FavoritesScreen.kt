package com.android.rxmvp

import android.view.View
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import org.hamcrest.Matcher

class FavoritesScreen : Screen<FavoritesScreen>() {

    val list = KRecyclerView(
        builder = { withId(R.id.rvFavoriteEvents) },
        itemTypeBuilder = { itemType(::Item) })

    class Item(parent: Matcher<View>) :
        KRecyclerItem<Item>(parent) {

        val tvName = KTextView(parent) { withId(R.id.tvName) }
        val tvDate = KTextView(parent) { withId(R.id.tvDate) }
        val ivFavorite = KImageView(parent) { withId(R.id.ivFavorite) }
    }
}