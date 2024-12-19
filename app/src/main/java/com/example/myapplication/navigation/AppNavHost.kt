package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.bookmark.navigation.BOOKMARK_ROUTE
import com.example.bookmark.navigation.navBookmarkScreen
import com.example.search.navigation.ROUTE_SEARCH_SCREEN
import com.example.search.navigation.navSearchScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ROUTE_SEARCH_SCREEN // Set the initial screen
    ) {
        navSearchScreen()

        navBookmarkScreen()
    }
}