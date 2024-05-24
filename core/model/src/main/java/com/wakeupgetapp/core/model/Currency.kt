package com.wakeupgetapp.core.model

data class Currency(
    val name: String,
    val code: String,
    val symbol: String,
    val rate: Double,
    val timestamp: Long = 0
)
