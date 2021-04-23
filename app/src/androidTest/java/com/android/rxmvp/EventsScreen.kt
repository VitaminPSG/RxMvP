package com.android.rxmvp

import android.net.Uri
import android.view.View
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.intent.KIntent
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.swiperefresh.KSwipeRefreshLayout
import com.agoda.kakao.text.KTextView
import org.hamcrest.Matcher

class EventsScreen : Screen<EventsScreen>() {


    val refreshLayout = KSwipeRefreshLayout { withId(R.id.swipeToRefresh) }

    val list = KRecyclerView(
        builder = { withId(R.id.rvEvents) },
        itemTypeBuilder = { itemType(::Item) })

    class Item(parent: Matcher<View>) :
        KRecyclerItem<Item>(parent) {

        val tvName = KTextView(parent) { withId(R.id.tvName) }
        val tvDate = KTextView(parent) { withId(R.id.tvDate) }
        val ivFavorite = KImageView(parent) { withId(R.id.ivFavorite) }
    }

}