package com.wakeupgetapp.network.internal.model

import com.google.gson.annotations.SerializedName

data class CurrencyTableDto(

    @SerializedName("date")
    val date: String,

    @SerializedName("usd")
    val rates: Map<String, Double>,
)