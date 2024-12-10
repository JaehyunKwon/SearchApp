package com.example.bookmarkscreen.navigation

import com.example.bookmarkscreen.BookmarkScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val BOOKMARK_ROUTE = "BOOKMARK_ROUTE"

fun NavGraphBuilder.navBookmarkScreen(onClick: () -> Unit) {
    composable(route = BOOKMARK_ROUTE) {
        BookmarkScreen(onClick = onClick)
    }
}