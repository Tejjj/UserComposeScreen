package com.example.assignment4_pagination.network

import com.example.assignment4_pagination.model.GithubSearchResponse
import com.example.assignment4_pagination.model.UserRepoDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {

    @GET("search/users")
    suspend fun getGitHubRepositories(
        @Query("q") username: String, @Query("page") page: Int, @Query("per_page") size: Int
    ): GithubSearchResponse

    @GET("users/{username}")
    suspend fun getUserGitHubDetails(@Path("username") username: String): Response<UserRepoDetails>

}