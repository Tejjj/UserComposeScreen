package com.example.assignment3.app.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment3.repository.DatabaseRepository
import com.example.assignment3.repository.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val dbRepository: DatabaseRepository) :
    ViewModel() {

    private val _userList = MutableStateFlow<List<User>>(emptyList())
    val userList: StateFlow<List<User>> = _userList.asStateFlow()

    private val _userData = MutableStateFlow<User?>(null)
    val userData = _userData.asStateFlow()


    fun loadUserList() {
        viewModelScope.launch(Dispatchers.IO) {
            var uList = dbRepository.getUserList()
            if (!uList.isEmpty()) {
                dbRepository.clearUserEntries(uList)
            }
            dbRepository.loadUserData()
        }
    }

    fun addUser() {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.insertUser()
        }
    }

    fun getObservableUserList() {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.getObservableUserList().collect() { userList ->
                _userList.value = userList
            }
        }
    }

    fun getUser(userId: Int) {
        viewModelScope.launch {
            _userData.value = dbRepository.getUser(userId)
        }
    }

    fun clearUserData(userList: List<User>) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.clearUserEntries(userList)
        }
    }

}