package com.example.assignment3.repository

import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun loadUserData(userList: List<User>)
    suspend fun getObservableUserList(): Flow<List<User>>
    suspend fun clearUserEntries(userList: List<User>)
    suspend fun insertUser(user: User)
}