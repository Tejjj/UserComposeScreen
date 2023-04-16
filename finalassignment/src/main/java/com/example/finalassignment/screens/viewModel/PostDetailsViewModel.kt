package com.example.finalassignment.screens.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalassignment.mode.PostDetailResponse
import com.example.finalassignment.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(private val repository: NetworkRepository) :
    ViewModel() {

    private val _postDetails = MutableStateFlow<PostDetailResponse?>(null)
    val postDetails: StateFlow<PostDetailResponse?> = _postDetails.asStateFlow()

    fun getPostDetail(userId: String, postId: String) = viewModelScope.launch {
        _postDetails.value = repository.getPostDetail(userId, postId)
    }

    fun deletePost(userId: String, postId: String) = viewModelScope.launch {
        if(_postDetails.value != null) {
            repository.deletePost(userId, postId)
        }

    }
}
