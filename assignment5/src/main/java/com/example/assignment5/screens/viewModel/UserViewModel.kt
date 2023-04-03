package com.example.assignment5.screens.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.assignment5.model.ProfileResponse
import com.example.assignment5.model.UserPostsResponse
import com.example.assignment5.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: NetworkRepository) : ViewModel() {
    private val _profile = MutableStateFlow<ProfileResponse?>(null)
    val userProfile: StateFlow<ProfileResponse?> = _profile.asStateFlow()

    val userPosts: Flow<PagingData<UserPostsResponse>>
    init {
        viewModelScope.launch {
            val response = repository.getUserProfile()
            _profile.value = response
        }

        userPosts = repository
            .getUserPosts()
            .cachedIn(viewModelScope)
    }
}