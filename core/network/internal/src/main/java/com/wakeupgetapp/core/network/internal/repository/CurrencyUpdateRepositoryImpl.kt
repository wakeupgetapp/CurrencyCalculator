package com.wakeupgetapp.core.network.internal.repository

import com.wakeupgetapp.core.model.Currency
import com.wakeupgetapp.core.data.repository.CurrencyUpdateRepository
import com.wakeupgetapp.core.model.CurrencyTable
import com.wakeupgetapp.core.network.internal.api.CurrencyApiService
import com.wakeupgetapp.core.network.internal.api.CurrencyBackupApiService
import com.wakeupgetapp.core.network.internal.model.CurrencyTableDto
import com.wakeupgetapp.core.network.internal.util.safeApiCall
import javax.inject.Inject

internal class CurrencyUpdateRepositoryImpl @Inject constructor(
    private val currencyApi: CurrencyApiService,
    private val backupCurrencyApi: CurrencyBackupApiService
) : CurrencyUpdateRepository {

    override suspend fun fetchLatestData() =
        safeApiCall { currencyApi.getCurrencyTable().toCurrencyTable() }

    override suspend fun fetchLatestDataBackup() =
        safeApiCall { backupCurrencyApi.getCurrencyTable().toCurrencyTable() }
}

internal fun CurrencyTableDto.toCurrencyTable(): CurrencyTable {
    val currencyList = mutableListOf<Currency>()
    this.rates.forEach { (key, value) ->
            currencyList.add(
                Currency(
                    name = "",
                    code = key.uppercase(),
                    symbol = "",
                    rate = value
                )
            )
    }

    return CurrencyTable(
        this.date,
        currencyList = currencyList
    )
}