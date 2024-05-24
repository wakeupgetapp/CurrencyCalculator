package com.wakeupgetapp.core.database.internal.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customExchangeEntity")
data class CustomExchangeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long,
    val base: String,
    val target: String,
    val rate: Double
)