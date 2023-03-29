package com.example.assignment4_pagination.di

import com.example.assignment4_pagination.network.GithubApiService
import com.example.assignment4_pagination.utils.Constants
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
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

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
                /**
                 * Only add [HttpLoggingInterceptor] on debug build
                 */
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(httpLoggingInterceptor)
                }
            }
            .build()
    }

    @Provides
    @Named("github")
    fun provideRetrofitFOrPaging(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.GITHUB_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi)) // Register Moshi as a JSON converter for serialization and deserialization of objects.
//            .addCallAdapterFactory(FlowerCallAdapterFactory.create()) // Register Flower as a response converter for supporting method return types other than Call<T>.
            .build()
    }

    @Provides
    fun providePagingApi(@Named("github") retrofit: Retrofit): GithubApiService =
        retrofit.create(GithubApiService::class.java)
}