package com.wakeupgetapp.core.data_test.repository

import com.wakeupgetapp.core.data.repository.CurrencyRepository
import com.wakeupgetapp.core.model.Currency
import com.wakeupgetapp.core.model.CurrencyTable
import com.wakeupgetapp.core.model.CustomExchange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeCurrencyRepository : CurrencyRepository {
    private var customExchangeCurrentId = 3L

    private val currenciesFlow = MutableStateFlow<List<Currency>>(emptyList())
    private val customExchangesFlow = MutableStateFlow<List<CustomExchange>>(emptyList())

    override fun getCurrencies(): Flow<List<Currency>> {
        return currenciesFlow
    }

    override suspend fun updateCurrencies(currencyTable: CurrencyTable) {
        currenciesFlow.value = currencyTable.currencyList
    }

    override suspend fun updateCurrencyTimestamp(code: String, timestamp: Long) {
        val currencies = currenciesFlow.value.toMutableList()
        val currencyIndex = currencies.indexOfFirst { it.code == code }
        if (currencyIndex != -1) {
            val updatedCurrency = currencies[currencyIndex].copy(timestamp = timestamp)
            currencies[currencyIndex] = updatedCurrency
            currenciesFlow.value = currencies
        }
    }

    override fun getCustomExchanges(): Flow<List<CustomExchange>> {
        return customExchangesFlow
    }

    override suspend fun saveCustomExchange(customExchange: CustomExchange) {
        val customExchanges = customExchangesFlow.value.toMutableList()
        customExchangeCurrentId += 1
        customExchanges.add(customExchange.copy(id = customExchangeCurrentId))
        customExchangesFlow.value = customExchanges
    }

    override suspend fun editCurrencyExchange(customExchange: CustomExchange) {
        val customExchanges = customExchangesFlow.value.toMutableList()
        val index = customExchanges.indexOfFirst { it.id == customExchange.id }
        if (index != -1) {
            customExchanges[index] = customExchange
            customExchangesFlow.value = customExchanges
        }
    }

    override suspend fun deleteCurrencyExchange(id: Long) {
        val customExchanges = customExchangesFlow.value.toMutableList()
        val index = customExchanges.indexOfFirst { it.id == id }
        if (index != -1) {
            customExchanges.removeAt(index)
            customExchangesFlow.value = customExchanges
        }
    }

    fun setInitialCurrencyList(value: List<Currency>) {
        currenciesFlow.value = value
    }

    fun setInitialCustomExchangeList(value: List<CustomExchange>) {
        customExchangesFlow.value = value
    }
}