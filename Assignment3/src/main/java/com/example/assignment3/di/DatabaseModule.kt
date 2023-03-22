package com.example.assignment3.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.assignment3.repository.DatabaseRepository
import com.example.assignment3.repository.room.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext

import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context = context.applicationContext,
            UserDatabase::class.java,
            "user.db"
        ).build()
    }

    @Provides
    fun provideUserDao(db: UserDatabase) = db.userDao

/*
    @Provides
    @Singleton
    fun provideCoroutineScope() = CoroutineScope(SupervisorJob())

    @Provides
    @UserState
    fun getUserDataState(scope: CoroutineScope, dbRepository: DatabaseRepository) : Boolean {
        var userPresent = false
        scope.launch(Dispatchers.IO) {
            var uList = dbRepository.getUserList()
            if(!uList.isEmpty()) {
                userPresent = true
            }
        }
        return userPresent

    }*/
}
/*

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class UserState*/
