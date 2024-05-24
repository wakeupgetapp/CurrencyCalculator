package com.wakeupgetapp.core.data.repository

import com.wakeupgetapp.core.model.CurrencyTable
import com.wakeupgetapp.core.network.model.CallResult

interface CurrencyUpdateRepository {
    suspend fun fetchLatestData(): CallResult<CurrencyTable>

    suspend fun fetchLatestDataBackup(): CallResult<CurrencyTable>
}