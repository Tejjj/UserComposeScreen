package com.example.assignment3.repository

import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun loadUserData()

    suspend fun insertUser()
    suspend fun getObservableUserList(): Flow<List<User>>
    suspend fun clearUserEntries(userList: List<User>)
}