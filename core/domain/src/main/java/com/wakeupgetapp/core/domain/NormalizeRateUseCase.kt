package com.wakeupgetapp.core.domain

import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class NormalizeRateUseCase @Inject constructor() {
    operator fun invoke(baseRate: Double, targetRate: Double): Double =
        (targetRate / baseRate).roundToDecimalPlaces().takeIf { it > 0.0 }
            ?: (targetRate / baseRate).takeToSignificantDecimal()

    operator fun invoke(rate: Double, switched: Boolean): Double =
        if (!switched) rate.roundToDecimalPlaces().takeIf { it > 0.0 }
            ?: rate.takeToSignificantDecimal()
        else (1 / rate).roundToDecimalPlaces().takeIf { it > 0.0 }
            ?: (1 / rate).takeToSignificantDecimal()

    private fun Double.roundToDecimalPlaces(scale: Int = 4): Double =
        BigDecimal(this).setScale(scale, RoundingMode.HALF_UP).toDouble()

    private fun Double.takeToSignificantDecimal(quantity: Int = 2, delimiter: String = "."): Double {
        if (this <= 0) return 0.0
        val str = this.toBigDecimal().toString()
        val integerPart = str.substringBefore(delimiter)
        val fractionalPart = str.substringAfter(delimiter)

        val numOfZeros = fractionalPart.takeWhile { it == '0' }.count()
        val significantFractionalPart = fractionalPart.take(numOfZeros + quantity)

        return "$integerPart.$significantFractionalPart".toDouble()
    }


}