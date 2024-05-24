package com.wakeupgetapp.network.internal.api

import com.wakeupgetapp.network.internal.model.CurrencyTableDto
import retrofit2.http.GET


internal interface CurrencyApiService {
    @GET("usd.min.json")
    suspend fun getCurrencyTable(): CurrencyTableDto
}