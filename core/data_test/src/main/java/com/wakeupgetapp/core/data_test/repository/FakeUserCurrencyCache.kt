package com.wakeupgetapp.core.data_test.repository

import com.wakeupgetapp.core.data.repository.UserCurrencyCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeUserCurrencyCache : UserCurrencyCache {
    private val baseCurrencyFlow = MutableStateFlow("")
    private val targetCurrencyFlow = MutableStateFlow("")
    private val customExchangeIdFlow = MutableStateFlow(0L)

    override suspend fun saveBaseCurrency(value: String) {
        baseCurrencyFlow.value = value
    }

    override val readBaseCurrency: Flow<String>
        get() = baseCurrencyFlow

    override suspend fun saveTargetCurrency(value: String) {
        targetCurrencyFlow.value = value
    }

    override val readTargetCurrency: Flow<String>
        get() = targetCurrencyFlow

    override suspend fun setCustomExchangeId(value: Long) {
        customExchangeIdFlow.value = value
    }

    override val customExchangeId: Flow<Long>
        get() = customExchangeIdFlow


    fun setInitialBaseCurrency(value: String) {
        baseCurrencyFlow.value = value
    }

    fun setInitialTargetCurrency(value: String) {
        targetCurrencyFlow.value = value
    }

    fun setInitialCustomExchangeId(value: Long) {
        customExchangeIdFlow.value = value
    }
}