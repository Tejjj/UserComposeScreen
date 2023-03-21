package com.example.assignment3.repository

import com.example.assignment3.repository.room.UserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseRepository @Inject constructor(private val userDao: UserDao) : Repository {
    override suspend fun loadUserData() {
        userDao.loadUserData(UserData.getUserList())
    }

    override suspend fun insertUser() {
        userDao.insertUser(UserData.adduser())
    }

    suspend fun getUserList(): List<User> {
        return userDao.getUserList()
    }
    override suspend fun getObservableUserList() : Flow<List<User>> {
        return userDao.getObservableUserList()
    }

    override suspend fun clearUserEntries(userList: List<User>) {
        userDao.deleteAllUser(userList)
    }

    suspend fun getUser(userId: Int):User {
        return userDao.getUser(userId)
    }

}