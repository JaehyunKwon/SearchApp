package com.example.search.navigation

import com.example.search.screen.SearchScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val ROUTE_SEARCH_SCREEN = "SEARCH_SCREEN_ROUTE"

fun NavGraphBuilder.navSearchScreen(onNextScreen: (String) -> Unit) {
    composable(route = ROUTE_SEARCH_SCREEN) {
        SearchScreen(onSubmitSearchText = onNextScreen)
    }
}