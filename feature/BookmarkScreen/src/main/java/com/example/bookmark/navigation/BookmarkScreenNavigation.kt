package com.example.bookmark.navigation

import com.example.bookmark.screen.BookmarkScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val BOOKMARK_ROUTE = "BOOKMARK_ROUTE"

fun NavGraphBuilder.navBookmarkScreen() {
    composable(route = BOOKMARK_ROUTE) {
        BookmarkScreen()
    }
}