package com.example.assignment5.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileResponse(
    @Json(name = "id")
    val userId: Int,

    val username: String,

    val email: String,

    @Json(name = "avatar")
    val profileImageUrl: String,

    val employment: Employment,

    val address: Address,

    val subscription: Subscription
)

@JsonClass(generateAdapter = true)
data class Employment(
    val title: String,

    @Json(name = "key_skill")
    val keySkill: String
)

@JsonClass(generateAdapter = true)
data class Address(
    val city: String,

    @Json(name = "street_name")
    val streetName: String,

    @Json(name = "street_address")
    val streetAddress: String,

    @Json(name = "zip_code")
    val zipCode: String,

    @Json(name = "state")
    val state: String,

    val country: String
)

@JsonClass(generateAdapter = true)
data class Subscription(
    val plan: String,
    val status: String
)
