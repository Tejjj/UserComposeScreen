package com.example.assignment3.repository.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.assignment3.repository.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


val Context.dataStore by preferencesDataStore(name = "user_datastore")

class DataStoreManager @Inject constructor(
    @ApplicationContext val context: Context
) {
    private object PreferencesKeys {
        val IS_USER_AVAILABLE = booleanPreferencesKey(name = "hasUserData")
    }

    suspend fun saveUser(userAdded: Boolean) {
        context.dataStore.edit { preference ->
            preference[PreferencesKeys.IS_USER_AVAILABLE] = userAdded
        }
    }

    val isUserPresent = context.dataStore.data.map { preference ->
        preference[PreferencesKeys.IS_USER_AVAILABLE] ?: false
    }
}