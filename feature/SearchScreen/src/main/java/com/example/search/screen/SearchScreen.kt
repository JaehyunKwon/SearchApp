package com.example.search.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.homescreen.R
import com.example.network.dto.DocumentsDto
import com.example.network.dto.NetworkResponse
import com.example.network.util.ApiResult
import com.example.search.viewmodel.ListDataViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun SearchScreen() {
    val viewModel: ListDataViewModel = hiltViewModel()
    val list by viewModel.imageState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            var searchText by remember { mutableStateOf("") }
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                modifier = Modifier.weight(1f),
                value = searchText,
                onValueChange = {
                    searchText = it
                })
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                viewModel.getImage(searchText)
            }) {
                Text(text = stringResource(id = R.string.feature_btn_search))
            }
        }
        ImageListView(items = list, viewModel)
    }
}

@Composable
fun ImageListView(items: ApiResult<NetworkResponse>, viewModel: ListDataViewModel) {
    when (items) {
        is ApiResult.Success -> {
            LazyColumn {
                items(items.data.documents.size) { index ->
                    BookmarkListItem(
                        document = items.data.documents[index],
                        viewModel = viewModel
                    )
                }
            }
        }

        is ApiResult.Error, is ApiResult.NetworkError -> {
            // 오류 상태 표시
            Text("Error: Unknown error")
        }
    }
}

@Composable
fun BookmarkListItem(
    document: DocumentsDto,
    viewModel: ListDataViewModel
) {
    var isBookmarked by remember { mutableStateOf(false) }

    // 북마크 여부 확인
    LaunchedEffect(document) {
        isBookmarked = viewModel.isBookmarked(document)
    }

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
                isBookmarked = !isBookmarked // 상태 토글
                viewModel.toggleBookmark(document)
            }
        ) {
            Icon(
                imageVector = if (isBookmarked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Bookmark Icon",
                tint = if (isBookmarked) Color.Red else Color.Gray
            )
        }
    }
}