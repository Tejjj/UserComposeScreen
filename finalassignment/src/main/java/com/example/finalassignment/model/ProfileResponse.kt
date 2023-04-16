package com.example.finalassignment.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileResponse(
    @Json(name = "id")
    val userId: String,

    val name: String?,

    @Json(name = "avatar")
    val profileImageUrl: String,

    val about: String?,

    val createdAt: String
)