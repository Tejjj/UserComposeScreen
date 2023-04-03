package com.example.assignment5.di

import com.example.assignment5.network.ProfileApiService
import com.example.assignment5.network.UserPostApiService
import com.example.assignment5.utils.Constants
import com.google.firebase.BuildConfig
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.annotation.ElementType
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun getLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .build()
    }

    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(httpLoggingInterceptor)
                }
            }
            .build()
    }

    @Provides
    @Singleton
    @Profile
    fun provideRetrofitForProfile(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.PROFILE_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi)) // Register Moshi as a JSON converter for serialization and deserialization of objects.
//            .addCallAdapterFactory(FlowerCallAdapterFactory.create()) // Register Flower as a response converter for supporting method return types other than Call<T>.
            .build()
    }

    @Provides
    @Singleton
    @UserPost
    fun provideRetrofitForPost(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.USER_POST_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi)) // Register Moshi as a JSON converter for serialization and deserialization of objects.
//            .addCallAdapterFactory(FlowerCallAdapterFactory.create()) // Register Flower as a response converter for supporting method return types other than Call<T>.
            .build()
    }

     @Provides
     @Profile
     fun provideProfileApi(@Profile retrofit: Retrofit): ProfileApiService =
         retrofit.create(ProfileApiService::class.java)

    @Provides
    @UserPost
    fun provideUserPostApi(@UserPost retrofit: Retrofit): UserPostApiService =
        retrofit.create(UserPostApiService::class.java)
}

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class Profile

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class UserPost
