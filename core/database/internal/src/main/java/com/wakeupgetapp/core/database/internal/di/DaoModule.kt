package com.wakeupgetapp.core.database.internal.di

import com.wakeupgetapp.core.database.internal.dao.CurrencyDao
import com.wakeupgetapp.core.database.internal.dao.CustomExchangeDao
import com.wakeupgetapp.core.database.internal.CurrencyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Singleton
    @Provides
    fun providesCurrencyDao(
        database: CurrencyDatabase,
    ): CurrencyDao = database.currencyDao()

    @Singleton
    @Provides
    fun providesCustomExchangeDao(
        database: CurrencyDatabase,
    ): CustomExchangeDao = database.customExchangeDao()

}