package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.bookmarkscreen.navigation.BOOKMARK_ROUTE
import com.example.bookmarkscreen.navigation.navBookmarkScreen
import com.example.searchscreen.navigation.ROUTE_SEARCH_SCREEN
import com.example.searchscreen.navigation.navSearchScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ROUTE_SEARCH_SCREEN // Set the initial screen
    ) {
        navSearchScreen(onNextScreen = { userName ->
            navController.navigate(BOOKMARK_ROUTE)
        })

        navBookmarkScreen {
            navController.navigate(ROUTE_SEARCH_SCREEN){
                popUpTo(ROUTE_SEARCH_SCREEN){
                    inclusive = true
                }
            }

        }
    }
}