package com.example.assignment4_pagination.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GithubSearchResponse(
    @Json(name = "total_count")
    val total_count: Long = 0,
    @Json(name = "incomplete_results")
    val incomplete_results: Boolean = false,

    @Json(name = "items")
    val repositories : List<UserRepository>
)


@JsonClass(generateAdapter = true)
data class UserRepository(
    @Json(name = "avatar_url")
    val profileImage: String?,

    @Json(name= "url")
    val profileUrl: String?,

    @Json(name = "login")
    val userName: String
)