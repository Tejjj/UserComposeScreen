package com.example.assignment4_pagination.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserRepoDetails(
    @Json(name = "login")
    val userName: String,

    @Json(name = "avatar_url")
    val profileImageUrl: String?,

    @Json(name = "name")
    val fullName: String?,

    @Json(name = "bio")
    val bioGraphy: String?,

    @Json(name = "public_repos")
    val repositories: Int = 0,

    @Json(name = "followers")
    val followers: Int = 0,

    @Json(name = "following")
    val following: Int = 0,

    @Json(name = "created_at")
    val createdAt: String,
)
