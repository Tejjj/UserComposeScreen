package com.example.finalassignment.utils

import com.example.finalassignment.model.UserPostsResponse

sealed class UserUIState {
    object Loading : UserUIState()
    object Empty : UserUIState()
    class Success(val userPosts: List<UserPostsResponse>) : UserUIState()
    class Failure(val errorText: String) : UserUIState()
}
