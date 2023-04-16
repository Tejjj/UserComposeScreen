package com.example.finalassignment.network

import com.example.finalassignment.mode.PostDetailResponse
import com.example.finalassignment.model.ProfileResponse
import com.example.finalassignment.model.UserPostsResponse
import com.example.finalassignment.model.UserProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileApiService {

   // @GET("api/v2/users"){
    @GET("users")
    suspend fun getUserProfileList(@Query("page") page: Int, @Query("limit") limit: Int): Response<List<ProfileResponse>>

    @GET("users/{userId}")
    suspend fun getUserDetails(@Path("userId") userId: String) : Response<UserProfileResponse>

    @GET("users/{userId}/Post")
    suspend fun getUserPosts(@Path("userId") userId: String) : Response<List<UserPostsResponse>>

    @GET("users/{userId}/Post/{postId}")
    suspend fun getPostDetail(@Path("userId") userId: String, @Path("postId") postId: String) : Response<PostDetailResponse>

    @POST("users/{userId}/Post")
    suspend fun createPost(@Path("userId") userId: String, @Body postModel: PostDetailResponse)

    @DELETE("users/{userId}/Post/{postId}")
    suspend fun deletePost(@Path("userId") userId: String, @Path("postId") postId: String)
}