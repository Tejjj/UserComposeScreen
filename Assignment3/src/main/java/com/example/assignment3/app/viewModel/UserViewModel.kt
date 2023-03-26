package com.example.assignment3.app.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment3.repository.DatabaseRepository
import com.example.assignment3.repository.User
import com.example.assignment3.repository.datastore.DataStoreManager
import com.example.assignment3.utils.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val dbRepository: DatabaseRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _userList = MutableStateFlow<List<User>>(emptyList())
    val userList: StateFlow<List<User>> = _userList.asStateFlow()

    private val _userData = MutableStateFlow<User?>(null)
    val userData = _userData.asStateFlow()

    private val _userAvailable = MutableStateFlow(false)
    var userAvailable = _userAvailable.asStateFlow()

    private val isUserAvailable: StateFlow<Boolean> = dataStoreManager.isUserPresent.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )

    fun savedUserState(userAdded: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreManager.saveUser(userAdded)
    }

    init {
        viewModelScope.launch {
            isUserAvailable.collect { userStatus ->
                _userAvailable.value = userStatus
            }
        }
    }

    fun loadUserList() {
        viewModelScope.launch {
            dbRepository.loadUserData(UserData.userList.toList())
        }
    }

    fun addUser() {
        viewModelScope.launch {
            dbRepository.insertUser(UserData.addUser())
        }
    }

    fun getObservableUserList() {
        viewModelScope.launch {
            dbRepository.getObservableUserList().collect { userList ->
                _userList.value = userList
            }
        }
    }

    fun getUser(userId: Int) {
        viewModelScope.launch {
            _userData.value = dbRepository.getUser(userId)
        }
    }

    private fun clearUserData(userList: List<User>) {
        viewModelScope.launch {
            dbRepository.clearUserEntries(userList)
            savedUserState(false)
        }
    }

    fun deleteAllUsers() {
        viewModelScope.launch {
            var uList = dbRepository.getUserList()

            if (uList.isEmpty()) return@launch

            clearUserData(uList)
        }
    }

}