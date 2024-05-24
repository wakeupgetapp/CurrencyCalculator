package com.wakeupgetapp.core.domain.util

fun Double.toBasicString(): String = this.toBigDecimal().toPlainString()