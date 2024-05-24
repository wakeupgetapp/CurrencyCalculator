package com.wakeupgetapp.core.database.internal.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_update")
data class CurrencyUpdateEntity(
    @PrimaryKey
    val id: Int = 1,
    val date: String,
)