package com.example.usercomposescreen.app.viewModel

import androidx.lifecycle.ViewModel
import com.example.usercomposescreen.data.User
import com.example.usercomposescreen.data.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserViewModel : ViewModel() {

    private val _mutableUserList = MutableStateFlow<List<User>>(emptyList())
    val userList : StateFlow<List<User>> = _mutableUserList.asStateFlow()


    init {
        _mutableUserList.value = UserData.posts.toList()/*.toMutableStateList()*/
    }

    private fun removeUser(user: User){

    }
}