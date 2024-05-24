package com.wakeupgetapp.core.domain

import com.wakeupgetapp.core.model.CurrencyUpdateState
import com.wakeupgetapp.core.data.repository.CurrencyRepository
import com.wakeupgetapp.network.model.CallResult
import com.wakeupgetapp.core.data.repository.CurrencyUpdateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CurrencyUpdateUseCase @Inject constructor(
    private val updateRepository: CurrencyUpdateRepository,
    private val currencyRepository: CurrencyRepository,
) : () -> Flow<CurrencyUpdateState> {
    override fun invoke(): Flow<CurrencyUpdateState> = flow {
        emit(CurrencyUpdateState.Loading)

        when (val latestCurrencyData = updateRepository.fetchLatestData()) {
            is CallResult.Success -> {
                currencyRepository.updateCurrencies(latestCurrencyData.value)
                emit(CurrencyUpdateState.Success)
            }

            is CallResult.NetworkFailure -> {
                emit(CurrencyUpdateState.CurrencyUpdateFailure)
            }

            is CallResult.Failure -> {
                backupRequest().collect {
                    emit(it)
                }
            }
        }
    }

    private fun backupRequest(): Flow<CurrencyUpdateState> = flow {
        when (val latestCurrencyData = updateRepository.fetchLatestDataBackup()) {
            is CallResult.Success -> {
                currencyRepository.updateCurrencies(latestCurrencyData.value)
                emit(CurrencyUpdateState.Success)
            }

            is CallResult.NetworkFailure -> {
                emit(CurrencyUpdateState.CurrencyUpdateFailure)
            }

            is CallResult.Failure -> {
                emit(
                    CurrencyUpdateState.Failure(
                    errorCode = latestCurrencyData.errorCode,
                    errorBody = latestCurrencyData.errorBody,
                    errorMessage = latestCurrencyData.errorMessage
                ))
            }
        }
    }
}