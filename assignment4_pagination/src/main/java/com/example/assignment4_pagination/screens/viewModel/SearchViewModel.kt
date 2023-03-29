package com.example.assignment4_pagination.screens.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.assignment4_pagination.model.UserRepository
import com.example.assignment4_pagination.network.GithubApiService
import com.example.assignment4_pagination.repositoty.NetworkRepository
import com.example.assignment4_pagination.repositoty.RepositoryPagingDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : ViewModel() {

    private val _search = MutableStateFlow("")

    val search = _search.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "",
        )

    val userSearchResults = search.debounce(300.milliseconds).flatMapLatest { query ->
        networkRepository
            .getGitHubRepositories(query)
            .cachedIn(viewModelScope)
    }

    fun setSearch(query: String) {
        _search.value = query
    }
}