package com.wakeupgetapp.core.model

data class CustomExchange(
    val id: Long,
    val timestamp: Long,
    val base: String,
    val target: String,
    val rate: Double
)