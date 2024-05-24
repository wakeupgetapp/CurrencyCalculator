package com.wakeupgetapp.core.database.internal.repository

import com.wakeupgetapp.core.data.repository.CurrencyRepository
import com.wakeupgetapp.core.model.Currency
import com.wakeupgetapp.core.model.CurrencyTable
import com.wakeupgetapp.core.model.CustomExchange
import com.wakeupgetapp.core.database.internal.dao.CurrencyDao
import com.wakeupgetapp.core.database.internal.dao.CustomExchangeDao
import com.wakeupgetapp.core.database.internal.model.CurrencyTimestampEntity
import com.wakeupgetapp.core.database.internal.util.toCurrencyList
import com.wakeupgetapp.core.database.internal.util.toCustomExchangeEntity
import com.wakeupgetapp.core.database.internal.util.toCustomExchangeList
import com.wakeupgetapp.core.database.internal.util.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class CurrencyRepositoryImpl @Inject constructor(
    private val currencyDao: CurrencyDao,
    private val customExchangeDao: CustomExchangeDao
) : CurrencyRepository {

    override fun getCurrencies(): Flow<List<Currency>> =
        currencyDao.getCurrencies().map { it.toCurrencyList() }

    override suspend fun updateCurrencies(currencyTable: CurrencyTable) {
        currencyDao.upsertCurrencyEntities(currencyTable.currencyList.map { it.toEntity() })
    }

    override suspend fun updateCurrencyTimestamp(code: String, timestamp: Long) {
        currencyDao.updateCurrencyTimestamp(
            CurrencyTimestampEntity(
                currencyCode = code,
                timestamp = timestamp
            )
        )
    }

    override fun getCustomExchanges(): Flow<List<CustomExchange>> =
        customExchangeDao.getAllCustomExchanges().map { it.toCustomExchangeList() }

    override suspend fun saveCustomExchange(customExchange: CustomExchange) {
        customExchangeDao.upsertCustomExchange(customExchange.toCustomExchangeEntity())
    }

    override suspend fun editCurrencyExchange(customExchange: CustomExchange) {
        customExchangeDao.upsertCustomExchange(customExchange.toCustomExchangeEntity())
    }

    override suspend fun deleteCurrencyExchange(id: Long) {
        customExchangeDao.deleteCustomExchangeById(id)
    }
}

