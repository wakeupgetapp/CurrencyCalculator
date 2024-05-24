package com.wakeupgetapp.core.data.repository

import kotlinx.coroutines.flow.Flow

interface UserCurrencyCache {
    suspend fun saveBaseCurrency(value: String)
    val readBaseCurrency: Flow<String>

    suspend fun saveTargetCurrency(value: String)
    val readTargetCurrency: Flow<String>

    suspend fun setCustomExchangeId(value: Long)
    val customExchangeId: Flow<Long>
}