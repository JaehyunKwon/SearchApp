package com.example.search.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.homescreen.R

@Composable
fun SearchScreen(
    onSubmitSearchText: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var searchText by remember { mutableStateOf("") }
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
            })
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onSubmitSearchText(searchText) }) {
            Text(text = stringResource(id = R.string.feature_btn_search))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSearchScreen() {
    SearchScreen { onSubmitSearchText ->
        Log.e("TAG", "searchText: $onSubmitSearchText")
    }
}