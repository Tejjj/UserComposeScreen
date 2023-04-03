package com.example.assignment5.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserPostsResponse(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,

    @Json(name = "download_url")
    val postImageUrl: String
)