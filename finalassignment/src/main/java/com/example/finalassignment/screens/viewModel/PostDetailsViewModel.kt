package com.example.finalassignment.screens.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.finalassignment.mode.PostDetailResponse
import com.example.finalassignment.repository.NetworkRepository
import com.example.finalassignment.screens.DeletePostWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val repository: NetworkRepository,
    application: Application
) :
    AndroidViewModel(application) {

    var context: Context = application

    private val _postDetails = MutableStateFlow<PostDetailResponse?>(null)
    val postDetails: StateFlow<PostDetailResponse?> = _postDetails.asStateFlow()

    fun getPostDetail(userId: String, postId: String) = viewModelScope.launch {
        _postDetails.value = repository.getPostDetail(userId, postId)
    }

    fun deletePost(userId: String, postId: String) = viewModelScope.launch {
        if(_postDetails.value != null) {
            deletePostWorkRequest(userId, postId)
        }
    }

    private fun deletePostWorkRequest(userId: String, postId: String) {
        val data = Data.Builder()
            .putString("userId", userId)
            .putString("postId", postId)
            .build()

        val deletePostRequest = OneTimeWorkRequestBuilder<DeletePostWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setInputData(data)
            .setInitialDelay(5, TimeUnit.MINUTES)
            .build()

        val workManager = WorkManager.getInstance(context)

        workManager.beginUniqueWork(
            "DeletePostRequest",
            ExistingWorkPolicy.KEEP,
            deletePostRequest
        ).enqueue()
    }
}
