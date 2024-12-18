package com.example.search.screen

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class)
@Composable
fun SearchScreen() {
    val viewModel: ListDataViewModel = hiltViewModel()
    val list by viewModel.imageState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var searchText by remember { mutableStateOf("") }
    val searchTextFlow = remember { MutableStateFlow("") }

    val focusManager = LocalFocusManager.current

    LaunchedEffect(searchText) {
        searchTextFlow.value = searchText
    }

    // 일정 시간 동안 변경이 없을 경우에만 API 호출
    LaunchedEffect(searchTextFlow) {
        searchTextFlow
            .debounce(1000L) // 1초간 변경이 없을 때 실행
            .collect { debouncedText ->
                if (debouncedText.isNotEmpty()) {
                    viewModel.getImage(debouncedText)
                    focusManager.clearFocus() // 키보드 내리기
                }
            }
    }

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
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                modifier = Modifier.weight(1f),
                value = searchText,
                onValueChange = {
                    searchText = it
                })
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        } else {
            ImageListView(items = list, viewModel)
        }
    }
}

@Composable
fun ImageListView(items: ApiResult<NetworkResponse>, viewModel: ListDataViewModel) {
    when (items) {
        is ApiResult.Success -> {
            LazyColumn {
                items(items.data.documents.size) { index ->
                    SearchListItem(
                        document = items.data.documents[index],
                        viewModel = viewModel
                    )
                }
            }
        }

        is ApiResult.Error -> {
            // 오류 상태 표시
            Text(stringResource(R.string.unknown_error))
        }
        is ApiResult.NetworkError -> {
            Text(stringResource(R.string.network_error))
        }
    }
}

@Composable
fun SearchListItem(
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