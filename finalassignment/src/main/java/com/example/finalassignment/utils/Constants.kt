package com.example.finalassignment.utils

import java.text.SimpleDateFormat
import java.util.Date

object Constants {

    const val BASE_URL = "https://64302d24b289b1dec4c30671.mockapi.io/api/v1/"
    const val PROFILE_API_BASE_URL = "https://random-data-api.com/"
    const val USER_POST_API_BASE_URL = "https://picsum.photos/"

    val currentDate: String = SimpleDateFormat("yyyy-MM-dd").format(Date())
}