package com.wakeupgetapp.core.database.internal.util

import com.wakeupgetapp.core.model.Currency
import com.wakeupgetapp.core.model.CustomExchange
import com.wakeupgetapp.core.database.internal.model.CurrencyEntity
import com.wakeupgetapp.core.database.internal.model.CurrencyWithTimestamp
import com.wakeupgetapp.core.database.internal.model.CustomExchangeEntity


internal fun CustomExchange.toCustomExchangeEntity() =
    CustomExchangeEntity(
        id = this.id,
        timestamp = this.timestamp,
        base = this.base,
        target = this.target,
        rate = this.rate
    )

internal fun List<CustomExchangeEntity>.toCustomExchangeList() =
    this.map {
        CustomExchange(
            id = it.id,
            timestamp = it.timestamp,
            base = it.base,
            target = it.target,
            rate = it.rate
        )
    }

internal fun Currency.toEntity() = CurrencyEntity(
    code = this.code,
    rate = this.rate
)

internal fun List<CurrencyWithTimestamp>.toCurrencyList(): List<Currency> =
    this.mapNotNull { entity ->
        val info = currencyInfoMap[entity.code.uppercase()]
        info?.let {
            Currency(
                name = it.first,
                code = entity.code,
                symbol = it.second,
                rate = entity.rate,
                timestamp = entity.timestamp ?: 0L
            )
        }
    }