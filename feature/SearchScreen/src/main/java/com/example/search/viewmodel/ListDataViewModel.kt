package com.example.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.BookmarkRepository
import com.example.data.usecase.IsBookmarkedUseCase
import com.example.data.usecase.ToggleBookmarkUseCase
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
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ListDataViewModel @Inject constructor(
    private val listRepository: ListRepository,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
    private val isBookmarkedUseCase: IsBookmarkedUseCase
) : ViewModel() {
    private val _imageState = MutableStateFlow<ApiResult<NetworkResponse>>(
        ApiResult.Success(
            NetworkResponse(
                Meta(0, 0, true), emptyList()
            )
        )
    )
    val imageState: StateFlow<ApiResult<NetworkResponse>> = _imageState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun getImage(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            listRepository.getImage(query)
                .onStart { _isLoading.value = true }
                .catch { e ->
                    when (e) {
                        is IOException -> {
                            _imageState.value = ApiResult.NetworkError(e)
                        }
                        else -> {
                            _imageState.value = ApiResult.Error(e.toString())
                        }
                    }
                    _isLoading.value = false
                }
                .collect { result ->
                    _imageState.value = result
                    _isLoading.value = false
                }
        }

    }

    // 북마크 추가 또는 삭제
    fun toggleBookmark(document: DocumentsDto) {
        viewModelScope.launch {
            toggleBookmarkUseCase(document)
        }
    }

    // 북마크 상태를 체크
    suspend fun isBookmarked(document: DocumentsDto): Boolean {
        return isBookmarkedUseCase(document)
    }
}