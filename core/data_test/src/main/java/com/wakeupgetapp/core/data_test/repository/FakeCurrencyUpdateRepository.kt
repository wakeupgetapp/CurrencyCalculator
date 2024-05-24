package com.wakeupgetapp.core.data_test.repository

import com.wakeupgetapp.core.data.repository.CurrencyUpdateRepository
import com.wakeupgetapp.core.model.CurrencyTable
import com.wakeupgetapp.core.network.model.CallResult

class FakeCurrencyUpdateRepository : CurrencyUpdateRepository {
    private var latestDataResult: CallResult<CurrencyTable> = CallResult.Failure(null, null, null)
    private var latestDataBackupResult: CallResult<CurrencyTable> = CallResult.Failure(null, null, null)

    override suspend fun fetchLatestData(): CallResult<CurrencyTable> {
        return latestDataResult
    }

    override suspend fun fetchLatestDataBackup(): CallResult<CurrencyTable> {
        return latestDataBackupResult
    }

    fun setInitialFetchLatestDataResult(result: CallResult<CurrencyTable>) {
        latestDataResult = result
    }

    fun setInitialFetchLatestDataBackupResult(result: CallResult<CurrencyTable>) {
        latestDataBackupResult = result
    }
}