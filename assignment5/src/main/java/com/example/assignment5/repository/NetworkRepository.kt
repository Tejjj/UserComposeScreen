package com.example.assignment5.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.assignment5.di.Profile
import com.example.assignment5.di.UserPost
import com.example.assignment5.model.ProfileResponse
import com.example.assignment5.model.UserPostsResponse
import com.example.assignment5.network.ProfileApiService
import com.example.assignment5.network.UserPostApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(
    @Profile private val apiService: ProfileApiService,
    @UserPost private val userPostService: UserPostApiService
) {

    suspend fun getUserProfile(): ProfileResponse? {
        val response = apiService.getUserProfile(1)
        if (!response.isSuccessful) return null

        return response.body()
    }

    fun getUserPosts(): Flow<PagingData<UserPostsResponse>> =
        Pager(config = PagingConfig(pageSize = 10)) {
            UserPostsPagingSource(apiService = userPostService)
        }.flow


}