package com.example.search.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.homescreen.R
import com.example.network.dto.Documents
import com.example.network.dto.NetworkResponse
import com.example.network.repository.ImageRepository
import com.example.network.util.ApiResult
import com.example.search.viewmodel.ImageDataViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun SearchScreen(
    viewModel: ImageDataViewModel = hiltViewModel()
) {
    val list by viewModel.imageState.collectAsState()

    Row (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
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
    ImageListView(items = list)
}

@Composable
fun ImageListView(items: ApiResult<NetworkResponse>) {
    LazyColumn {
        items(items.data.documents.size) { index ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null
                )
                //Text(text = items.documents[index])
            }
        }
    }
}

class FakeImageRepository : ImageRepository {
    // 더미 데이터를 반환하는 getImage 함수 구현
    override suspend fun getImage(query: String): Flow<ApiResult<NetworkResponse>> =
        /*flow {
        emit(ApiResult.Success(Documents(// 더미 Documents 객체 생성
            collection = "",
            thumbnailUrl = "",
            imageUrl = "",
            width = 0,
            height = 0,
            displaySitename = "",
            docUrl = "",
            datetime = ""
        )))*/
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSearchScreen() {
    val viewModel = ImageDataViewModel(FakeImageRepository())

    SearchScreen(viewModel = viewModel)
}