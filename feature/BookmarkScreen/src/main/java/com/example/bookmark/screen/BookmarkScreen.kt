package com.example.bookmark.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bookmark.viewmodel.BookmarkViewModel
import com.example.data.entity.DocumentsEntity
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun BookmarkScreen(viewModel: BookmarkViewModel = hiltViewModel()) {
    val bookmarks by viewModel.bookmarkState.collectAsState(emptyList())

    LazyColumn {
        items(bookmarks.size) { data ->
            BookmarkItem(bookmarks[data], viewModel)
        }
    }
}

@Composable
fun BookmarkItem(
    document: DocumentsEntity,
    viewModel: BookmarkViewModel
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // 이미지
        GlideImage(
            imageModel = { document.thumbnail_url },
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // 텍스트
        Text(
            text = document.collection,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )

        // 북마크 버튼
        IconButton(
            onClick = {
                viewModel.toggleBookmark(document)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Bookmark Icon",
                tint = Color.Red
            )
        }
    }
}