package com.example.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.BookmarkRepository
import com.example.network.dto.DocumentsDto
import com.example.network.dto.Meta
import com.example.network.dto.NetworkResponse
import com.example.network.repository.ListRepository
import com.example.network.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListDataViewModel @Inject constructor(
    private val listRepository: ListRepository,
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {
    private val _imageState = MutableStateFlow<ApiResult<NetworkResponse>>(
        ApiResult.Success(
            NetworkResponse(
                Meta(0, 0, true), emptyList()
            )
        )
    )
    val imageState: StateFlow<ApiResult<NetworkResponse>> = _imageState.asStateFlow()

    fun getImage(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            listRepository.getImage(query)
                .onStart { /* 로딩 처리 */ }
                .catch { e -> _imageState.value = ApiResult.NetworkError(e) }
                .collect { result ->
                    _imageState.value = result
                }
        }

    }

    // 북마크 추가 또는 삭제
    fun toggleBookmark(document: DocumentsDto) {
        viewModelScope.launch {
            if (isBookmarked(document)) {
                bookmarkRepository.deleteBookmark(document)
            } else {
                bookmarkRepository.saveBookmark(document)
            }
        }
    }

    // 북마크 상태를 체크
    suspend fun isBookmarked(document: DocumentsDto): Boolean {
        return bookmarkRepository.isBookmarked(document)
    }
}