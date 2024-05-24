package com.wakeupgetapp.core.database.internal.di

import com.wakeupgetapp.core.data.repository.CurrencyRepository
import com.wakeupgetapp.core.database.internal.repository.CurrencyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class CurrencyRepositoryModule {

    @Binds
    internal abstract fun bindsCurrencyRepository(
        currencyRepository: CurrencyRepositoryImpl
    ): CurrencyRepository
}