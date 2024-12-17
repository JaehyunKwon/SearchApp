package com.example.bookmark.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.entity.DocumentsEntity
import com.example.data.repository.BookmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {
    private val _bookmarkState = MutableStateFlow <Flow<List<DocumentsEntity>>>(flowOf(emptyList()))
    val bookmarkState: StateFlow<Flow<List<DocumentsEntity>>> = _bookmarkState.asStateFlow()

    fun getBookmark() {
        viewModelScope.launch {
            _bookmarkState.value = bookmarkRepository.getBookmark()
        }
    }
}