package com.wakeupgetapp.network.internal.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object UrlModule {

    @BaseUrl
    @Provides
    fun provideBaseUrl() =
        "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/"

    @BackupUrl
    @Provides
    fun provideBackupUrl() = "https://latest.currency-api.pages.dev/v1/currencies/"

}