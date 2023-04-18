package com.example.finalassignment.screens.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalassignment.datastore.DataStoreManager
import com.example.finalassignment.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val dataStoreManager: DataStoreManager
) : ViewModel() {
    val language: StateFlow<String> = dataStoreManager.language.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = "en"
    )

    fun saveLanguage(languageCode: String) = viewModelScope.launch {
        dataStoreManager.saveLanguage(languageCode = languageCode)
    }
}