package com.example.usercomposescreen.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalRepository @Inject constructor() : Repository {

    override fun fetchUserList(): List<User> {
        return UserData.posts.toList()
    }
}