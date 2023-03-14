package com.example.usercomposescreen.data

interface Repository {
    fun fetchUserList(): List<User>
}