package com.wakeupgetapp.core.database.internal.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencyList")
data class CurrencyEntity(
    @PrimaryKey
    val code: String,
    val rate: Double
)