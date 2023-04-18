package com.example.finalassignment.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.finalassignment.mode.PostDetailResponse
import com.example.finalassignment.model.ProfileResponse
import com.example.finalassignment.model.UserPostsResponse
import com.example.finalassignment.model.UserProfileResponse
import com.example.finalassignment.network.ProfileApiService
import com.example.finalassignment.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(
    private val apiService: ProfileApiService
) {
    fun getUserProfileList(): Flow<PagingData<ProfileResponse>> =
        Pager(config = PagingConfig(pageSize = 10)) {
            UserProfilePagingSource(apiService = apiService)
        }.flow

    suspend fun getUserDetails(userId: String): UserProfileResponse? {
        val response = apiService.getUserDetails(userId)
        if (response.isSuccessful) {
            return response.body()
        }
        return null
    }

    suspend fun getUserPosts(userId: String): Resource<List<UserPostsResponse>> {
        val response = apiService.getUserPosts(userId)
        val responseBody = response.body()
        if (response.isSuccessful && responseBody != null) {
            return Resource.Success(responseBody, "Success")
        }
        return Resource.Error("Network request failed")
    }

    suspend fun getPostDetail(userId: String, postId: String): PostDetailResponse? {
        val response = apiService.getPostDetail(userId, postId)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun createPost(post: PostDetailResponse) {
        apiService.createPost(post.userId, post)
    }

    suspend fun deletePost(userId: String, postId: String) {
        apiService.deletePost(userId, postId)
    }

}