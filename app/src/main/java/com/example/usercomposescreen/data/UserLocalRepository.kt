package com.example.usercomposescreen.data

class UserLocalRepository : Repository {

    override fun fetchUserList(): List<User> {
        return UserData.posts.toList()
    }
}