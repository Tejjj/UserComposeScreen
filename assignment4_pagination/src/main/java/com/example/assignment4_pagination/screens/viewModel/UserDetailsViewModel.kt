package com.example.assignment4_pagination.screens.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment4_pagination.model.UserRepoDetails
import com.example.assignment4_pagination.repositoty.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : ViewModel() {


    private val _userData = MutableStateFlow<UserRepoDetails?>(null)
    val userData = _userData.asStateFlow()


    fun getUserRepoDetails(userName: String) {
        viewModelScope.launch {
            var userDetails = networkRepository.getUserGitHubDetails(userName)
            _userData.value = userDetails
        }
    }

}