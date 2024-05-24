package com.wakeupgetapp.core.database.internal.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencyTimestampList")
data class CurrencyTimestampEntity(

    @PrimaryKey
    @ColumnInfo(name = "currencyCode")
    val currencyCode: String,
    val timestamp: Long
)

