package com.example.assignment4_pagination.repositoty

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.assignment4_pagination.model.UserRepoDetails
import com.example.assignment4_pagination.model.UserRepository
import com.example.assignment4_pagination.network.GithubApiService
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(
    private val apiService: GithubApiService
) {
    fun getGitHubRepositories(username: String): Flow<PagingData<UserRepository>> =
        Pager(config = PagingConfig(pageSize = 10)) {
            RepositoryPagingDataSource(apiService = apiService, searchQuery = username)
        }.flow

    suspend fun getUserGitHubDetails(username: String): UserRepoDetails? {
        val response = apiService.getUserGitHubDetails(username)

        if (!response.isSuccessful) return null

        return response.body()
    }
}