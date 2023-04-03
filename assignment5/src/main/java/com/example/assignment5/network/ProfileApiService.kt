package com.example.assignment5.network

import com.example.assignment5.di.Profile
import com.example.assignment5.model.ProfileResponse
import com.example.assignment5.model.UserPostsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Named

interface ProfileApiService {

    @GET("api/v2/users")
    suspend fun getUserProfile(@Query("size") size: Int): Response<ProfileResponse>
}