package com.wakeupgetapp.core.database.internal.di

import android.content.Context
import androidx.room.Room
import com.wakeupgetapp.core.database.internal.CurrencyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesCurrencyDatabase(
        @ApplicationContext context: Context,
    ): CurrencyDatabase = Room.databaseBuilder(
        context,
        CurrencyDatabase::class.java,
        "currencyList-database",
    ).build()
}
