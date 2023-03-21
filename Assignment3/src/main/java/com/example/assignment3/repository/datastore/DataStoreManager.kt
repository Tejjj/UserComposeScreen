package com.example.assignment3.repository.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.assignment3.repository.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


val Context.dataStore by preferencesDataStore(name = "my_datastore")

class DataStoreManager @Inject constructor(
    @ApplicationContext val context: Context
) {
    private object PreferencesKeys {
        val USER_ID = intPreferencesKey(name = "userId")
        val NAME = stringPreferencesKey(name = "name")
        val FULL_NAME = stringPreferencesKey(name = "fullname")
        val EMAIL = stringPreferencesKey(name = "email")
    }

    suspend fun saveUserIno(user: User) {
        context.dataStore.edit { preference ->
            preference[PreferencesKeys.USER_ID] = user.userId
            preference[PreferencesKeys.NAME] = user.name
            preference[PreferencesKeys.FULL_NAME] = user.fullname
            preference[PreferencesKeys.EMAIL] = user.email
        }
    }

    val userPreferenceFlow: Flow<User> = context.dataStore.data.map { preference ->
        val userId = preference[PreferencesKeys.USER_ID] ?: 0
        val name = preference[PreferencesKeys.NAME] ?: "name"
        val fullName = preference[PreferencesKeys.FULL_NAME] ?: "full name"
        val email = preference[PreferencesKeys.EMAIL] ?: "email"

        User(userId, name, fullName, email)
    }
}