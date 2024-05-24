package com.wakeupgetapp.core.data.repository

import com.wakeupgetapp.core.model.Currency
import com.wakeupgetapp.core.model.CurrencyTable
import com.wakeupgetapp.core.model.CustomExchange
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    fun getCurrencies() : Flow<List<Currency>>

    suspend fun updateCurrencies(currencyTable: CurrencyTable)

    suspend fun updateCurrencyTimestamp(code: String, timestamp: Long)

    fun getCustomExchanges() : Flow<List<CustomExchange>>

    suspend fun saveCustomExchange(customExchange: CustomExchange)

    suspend fun editCurrencyExchange(customExchange: CustomExchange)

    suspend fun deleteCurrencyExchange(id: Long)
}