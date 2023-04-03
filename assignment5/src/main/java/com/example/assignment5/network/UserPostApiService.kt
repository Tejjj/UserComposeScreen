package com.example.assignment5.network

import com.example.assignment5.di.UserPost
import com.example.assignment5.model.UserPostsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Named

interface UserPostApiService {
    @GET("v2/list")
    suspend fun getUserPosts(
        @Query("page") page: Int,
        @Query("limit") pageSize: Int
    ): Response<List<UserPostsResponse>>
}