package com.wakeupgetapp.core.database.internal.model

import androidx.room.ColumnInfo

data class CurrencyWithTimestamp(
    @ColumnInfo(name = "code") val code: String,
    @ColumnInfo(name = "rate") val rate: Double,
    val timestamp: Long?
)