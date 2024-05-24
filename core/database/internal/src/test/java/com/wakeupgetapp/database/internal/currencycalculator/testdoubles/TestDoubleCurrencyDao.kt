package com.wakeupgetapp.database.internal.currencycalculator.testdoubles

import com.wakeupgetapp.core.database.internal.dao.CurrencyDao
import com.wakeupgetapp.core.database.internal.model.CurrencyEntity
import com.wakeupgetapp.core.database.internal.model.CurrencyTimestampEntity
import com.wakeupgetapp.core.database.internal.model.CurrencyWithTimestamp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update


class TestDoubleCurrencyDao : CurrencyDao {
    private val currencyWithTimestampStateFlow = MutableStateFlow(emptyList<CurrencyTimestampEntity>())
    private val currencyEntityStateFlow = MutableStateFlow(emptyList<CurrencyEntity>())
    private val currencyWithTimestamp: Flow<List<CurrencyWithTimestamp>> =
        combine(
        currencyEntityStateFlow,
        currencyWithTimestampStateFlow
    ) { entityRate, entityTimestamp ->
        val list = mutableListOf<CurrencyWithTimestamp>()
        entityRate.forEach {
            list.add(
                CurrencyWithTimestamp(
                    code = it.code,
                    rate = it.rate,
                    timestamp = entityTimestamp.firstOrNull { entityTimestamp ->
                        entityTimestamp.currencyCode == it.code
                    }?.timestamp ?: 0
                )
            )
        }
        list
    }

    override suspend fun upsertCurrencyEntities(currencyEntities: List<CurrencyEntity>) {
        currencyEntityStateFlow.update { currentList ->
            val updatedList = currentList.toMutableList()
            currencyEntities.forEach { newEntity ->
                val index = updatedList.indexOfFirst { it.code == newEntity.code }
                if (index >= 0) {
                    updatedList[index] = newEntity
                } else {
                    updatedList.add(newEntity)
                }
            }
            updatedList.toList()
        }
    }

    override fun getCurrencies(): Flow<List<CurrencyWithTimestamp>> = currencyWithTimestamp


    override suspend fun updateCurrencyTimestamp(currency: CurrencyTimestampEntity) {
        currencyWithTimestampStateFlow.update { currentList ->
            val updatedList = currentList.toMutableList()
            updatedList.remove(updatedList.firstOrNull { it.currencyCode == currency.currencyCode })
            updatedList.add(currency)
            updatedList.toList()
        }
    }

}