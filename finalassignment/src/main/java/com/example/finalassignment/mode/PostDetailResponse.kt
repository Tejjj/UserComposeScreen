package com.example.finalassignment.mode

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostDetailResponse(
    @Json(name = "id")
    val postId: String,

    val userId: String,

    @Json(name = "url")
    val postImageUrl: String,

    val caption: String?,

    val createdAt: String,

    val likesCount: Int = 0,

    val commentsCount: Int = 0,
    )
