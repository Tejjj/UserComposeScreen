package com.example.assignment3.di

import android.content.Context
import com.example.assignment3.repository.datastore.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    fun provideDataStoreManager(@ApplicationContext context: Context) : DataStoreManager {
        return DataStoreManager(context)
    }
}