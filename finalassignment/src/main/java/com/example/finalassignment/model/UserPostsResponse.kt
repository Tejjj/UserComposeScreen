package com.example.finalassignment.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserPostsResponse(
    @Json(name = "id")
    val postId: String,

    val userId: String,

    @Json(name = "url")
    val postImageUrl: String
)