package com.wakeupgetapp.core.network.model

sealed class CallResult<out T> {
    data class Success<out T>(val value: T): CallResult<T>()
    data class Failure(
        val errorCode: Int?,
        val errorBody: String?,
        val errorMessage: String?
    ) : CallResult<Nothing>()
    data object NetworkFailure: CallResult<Nothing>()

}