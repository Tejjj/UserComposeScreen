package com.example.finalassignment.screens.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalassignment.mode.PostDetailResponse
import com.example.finalassignment.model.UserPostsResponse
import com.example.finalassignment.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(private val repository: NetworkRepository) :
    ViewModel() {
    fun createPost(post: PostDetailResponse) =
        viewModelScope.launch {
            repository.createPost(post)
        }


}
