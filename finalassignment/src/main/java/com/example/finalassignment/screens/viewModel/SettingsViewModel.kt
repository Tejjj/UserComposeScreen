package com.example.finalassignment.screens.viewModel

import androidx.lifecycle.ViewModel
import com.example.finalassignment.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: NetworkRepository) :
    ViewModel() {

        init {
            
        }

}
