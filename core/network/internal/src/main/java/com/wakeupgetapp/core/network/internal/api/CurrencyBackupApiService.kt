package com.wakeupgetapp.core.network.internal.api

import com.wakeupgetapp.core.network.internal.model.CurrencyTableDto
import retrofit2.http.GET


internal interface CurrencyBackupApiService {
    @GET("usd.min.json")
    suspend fun getCurrencyTable(): CurrencyTableDto
}