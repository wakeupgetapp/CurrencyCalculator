package com.wakeupgetapp.network.internal.di

import com.wakeupgetapp.network.internal.repository.CurrencyUpdateRepositoryImpl
import com.wakeupgetapp.core.data.repository.CurrencyUpdateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class CurrencyUpdateRepositoryModule {

    @Binds
    internal abstract fun bindsCurrencyUpdateRepository(
        currencyUpdateRepository: CurrencyUpdateRepositoryImpl
    ): CurrencyUpdateRepository
}