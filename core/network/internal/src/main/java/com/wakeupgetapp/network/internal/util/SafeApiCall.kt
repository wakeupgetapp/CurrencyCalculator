package com.wakeupgetapp.network.internal.util

import com.wakeupgetapp.network.model.CallResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): CallResult<T> {
    return withContext(Dispatchers.IO) {
        try {
            CallResult.Success(apiCall())
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    val responseBodyString = errorBodyToString(throwable.response()?.errorBody())
                    CallResult.Failure(
                        throwable.code(),
                        responseBodyString,
                        throwable.message
                    )
                }
                else -> {
                    CallResult.NetworkFailure
                }
            }
        }
    }
}


fun errorBodyToString(errorBody: ResponseBody?): String {
    var response = "Could not parse JSON body"

    if (errorBody != null) {
        try {
            response = JSONObject(errorBody.charStream().readText()).toString()
        } catch (e: JSONException) {
            return response
        }
    }
    return response
}