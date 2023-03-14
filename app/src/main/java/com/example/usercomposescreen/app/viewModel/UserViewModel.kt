package com.example.usercomposescreen.app.viewModel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usercomposescreen.data.User
import com.example.usercomposescreen.data.UserLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel : ViewModel() {

    private var itemList = UserLocalRepository().fetchUserList()
    private val mutableUserList =  itemList.toMutableStateList()

    private val _userListFlow = MutableStateFlow(mutableUserList)
    val userListFlow: StateFlow<List<User>> get() = _userListFlow

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if(itemList.isEmpty()) {
                    itemList = UserLocalRepository().fetchUserList()
                }
            }
        }
    }

    fun removeUserAtIndex(position: Int) {
        if(position >=0) {
            mutableUserList.remove(mutableUserList[position])
        }
    }

    fun removeUser(user: User) {
        val index = mutableUserList.indexOf(user)
        (index >=0).apply {
            mutableUserList.remove(mutableUserList[index])
        }

    }
}