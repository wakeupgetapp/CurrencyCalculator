package com.wakeupgetapp.core.datastore.internal.di

import com.wakeupgetapp.core.datastore.internal.repository.UserCurrencyCacheImpl
import com.wakeupgetapp.core.data.repository.UserCurrencyCache
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UserCurrencyCacheModule {
    @Binds
    internal abstract fun bindsUserCurrencyCache(
        userCurrencyCache: UserCurrencyCacheImpl,
    ): UserCurrencyCache
}