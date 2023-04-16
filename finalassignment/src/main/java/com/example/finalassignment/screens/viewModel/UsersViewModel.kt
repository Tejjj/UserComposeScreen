package com.example.finalassignment.screens.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.finalassignment.model.ProfileResponse
import com.example.finalassignment.model.UserPostsResponse
import com.example.finalassignment.repository.NetworkRepository
import com.example.finalassignment.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val repository: NetworkRepository) : ViewModel() {

    val userProfileList: Flow<PagingData<ProfileResponse>> =
        repository.getUserProfileList().cachedIn(viewModelScope)
}


