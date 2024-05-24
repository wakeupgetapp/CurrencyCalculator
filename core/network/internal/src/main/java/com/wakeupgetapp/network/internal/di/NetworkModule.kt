package com.wakeupgetapp.network.internal.di

import com.wakeupgetapp.network.internal.api.CurrencyApiService
import com.wakeupgetapp.network.internal.api.CurrencyBackupApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            ).build()


    @BaseRetrofit
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, @BaseUrl baseUrl: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

    @BackupRetrofit
    @Singleton
    @Provides
    fun provideBackupRetrofit(okHttpClient: OkHttpClient, @BackupUrl backupUrl: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(backupUrl)
            .client(okHttpClient)
            .build()


    @Provides
    @Singleton
    fun provideApiService(@BaseRetrofit retrofit: Retrofit): CurrencyApiService =
        retrofit.create(CurrencyApiService::class.java)

    @Provides
    @Singleton
    fun provideBackupApiService(@BackupRetrofit retrofit: Retrofit): CurrencyBackupApiService =
        retrofit.create(CurrencyBackupApiService::class.java)

}