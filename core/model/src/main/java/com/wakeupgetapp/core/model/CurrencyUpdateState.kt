package com.wakeupgetapp.core.model

sealed interface CurrencyUpdateState {
    data object Success : CurrencyUpdateState
    data object Loading : CurrencyUpdateState
    data object CurrencyUpdateFailure : CurrencyUpdateState
    data class Failure(
        val errorCode: Int?,
        val errorBody: String?,
        val errorMessage: String?
    ) : CurrencyUpdateState
}