package com.example.finalassignment.screens.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalassignment.model.UserPostsResponse
import com.example.finalassignment.model.UserProfileResponse
import com.example.finalassignment.repository.NetworkRepository
import com.example.finalassignment.utils.Resource
import com.example.finalassignment.utils.UserUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: NetworkRepository) : ViewModel() {

    private val _userProfile = MutableStateFlow<UserProfileResponse?>(null)
    val userProfile: StateFlow<UserProfileResponse?> = _userProfile.asStateFlow()

    private val _userPosts = MutableStateFlow<List<UserPostsResponse>>(emptyList())

    private val _userPostState = MutableStateFlow<UserUIState>(UserUIState.Empty)
    val userPostState: StateFlow<UserUIState> = _userPostState.asStateFlow()

    fun getUserProfile(userId: String) {
        viewModelScope.launch {
            val response = repository.getUserDetails(userId)
            _userProfile.value = response
        }
    }
    fun getUserPosts(userId: String) {
        viewModelScope.launch {
            _userPostState.value = UserUIState.Loading
            when(val result = repository.getUserPosts(userId)) {
                is Resource.Error -> {
                    _userPostState.value = UserUIState.Failure(result.message)
                    // _userPosts.value = emptyList()
                }
                is Resource.Success -> {
                    _userPostState.value = UserUIState.Success(result.data as List<UserPostsResponse>)
                    //_userPosts.value = result.data as List
                }
            }
        }
    }

}
