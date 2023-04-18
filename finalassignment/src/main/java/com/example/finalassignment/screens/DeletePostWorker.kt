package com.example.finalassignment.screens

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.finalassignment.repository.NetworkRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DeletePostWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val repository: NetworkRepository
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        return try {
            val userId = inputData.getString("userId")
            val postId = inputData.getString("postId")
            if (userId != null && postId != null) {
                repository.deletePost(userId, postId)
            }
            return Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
