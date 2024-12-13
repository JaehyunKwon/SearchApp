package com.example.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.dto.Documents
import com.example.network.dto.NetworkResponse
import com.example.network.repository.ImageRepository
import com.example.network.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDataViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {
    private val _imageState = MutableStateFlow<ApiResult<NetworkResponse>>(ApiResult.Loading)
    val imageState: StateFlow<ApiResult<NetworkResponse>> = _imageState.asStateFlow()

    fun getImage(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            imageRepository.getImage(query).collect { response ->
                _imageState.value = response
            }
        }

    }
}