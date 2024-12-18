package com.example.bookmark.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.entity.DocumentsEntity
import com.example.data.repository.BookmarkRepository
import com.example.data.usecase.ToggleBookmarkUseCase
import com.example.data.util.DocumentsMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {
    private val _bookmarkState = MutableStateFlow <List<DocumentsEntity>>(emptyList())
    val bookmarkState: StateFlow<List<DocumentsEntity>> = _bookmarkState.asStateFlow()

    init {
        getBookmark()
    }

    private fun getBookmark() {
        viewModelScope.launch {
            bookmarkRepository.getBookmark()
                .collect { bookmark ->
                    _bookmarkState.value = bookmark
                }
        }
    }

    fun toggleBookmark(document: DocumentsEntity) {
        viewModelScope.launch {
            val data = DocumentsMapper.entityToDto(document)
            toggleBookmarkUseCase(data)
        }
    }
}