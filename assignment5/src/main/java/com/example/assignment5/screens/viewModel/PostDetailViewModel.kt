package com.example.assignment5.screens.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment5.model.ProfileResponse
import com.example.assignment5.model.UserPostsResponse
import com.example.assignment5.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val repository: NetworkRepository
) : ViewModel() {

    private val _profile = MutableStateFlow<UserPostsResponse?>(null)
    val userProfile: StateFlow<UserPostsResponse?> = _profile.asStateFlow()

    fun getUserById(userId: String) {
        viewModelScope.launch {
            val result = repository.getUserById(userId)
            _profile.value = result
        }
    }


}