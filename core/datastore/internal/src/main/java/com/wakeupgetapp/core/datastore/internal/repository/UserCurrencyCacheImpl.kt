package com.wakeupgetapp.core.datastore.internal.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.wakeupgetapp.core.data.repository.UserCurrencyCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class UserCurrencyCacheImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserCurrencyCache {

    override suspend fun saveBaseCurrency(value: String) {
        val dataStoreKey = BASE_CURRENCY_KEY
        dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    override val readBaseCurrency: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[BASE_CURRENCY_KEY] ?: ""
        }

    override suspend fun saveTargetCurrency(value: String) {
        val dataStoreKey = TARGET_CURRENCY_KEY
        dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    override val readTargetCurrency: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[TARGET_CURRENCY_KEY] ?: ""
        }

    override suspend fun setCustomExchangeId(value: Long) {
        val dataStoreKey = CUSTOM_EXCHANGE_ID_KEY
        dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    override val customExchangeId: Flow<Long> = dataStore.data
        .map { preferences ->
            preferences[CUSTOM_EXCHANGE_ID_KEY] ?: -1L
        }

    companion object CurrencyPreferencesKeys {
        val BASE_CURRENCY_KEY = stringPreferencesKey("base_currency")
        val TARGET_CURRENCY_KEY = stringPreferencesKey("target_currency")
        val CUSTOM_EXCHANGE_ID_KEY = longPreferencesKey("custom_exchange_id")
    }
}