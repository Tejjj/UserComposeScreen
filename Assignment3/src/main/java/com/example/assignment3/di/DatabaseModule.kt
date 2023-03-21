package com.example.assignment3.di

import android.content.Context
import androidx.room.Room
import com.example.assignment3.repository.room.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext

import dagger.hilt.components.SingletonComponent

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
}