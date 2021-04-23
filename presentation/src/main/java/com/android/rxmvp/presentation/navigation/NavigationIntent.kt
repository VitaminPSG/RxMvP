package com.android.rxmvp.presentation.navigation

sealed class NavigationIntent {
    object OpenEvents : NavigationIntent(), Screen
    object OpenFavorites : NavigationIntent(), Screen
    data class OpenWeb(val url: String) : NavigationIntent(), Event
}

interface Screen {

}

interface Event {

}