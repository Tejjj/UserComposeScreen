package com.example.finalassignment.utils

sealed class Resource<T>(val data: T?, val message: String) {
    class Success<T>(data: T, baseCode: String) : Resource<T>(data, baseCode)
    class Error<T>(message: String) : Resource<T>(null, message)
}