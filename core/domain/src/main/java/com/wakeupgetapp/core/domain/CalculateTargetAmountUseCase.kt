package com.wakeupgetapp.core.domain

import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class CalculateTargetAmountUseCase @Inject constructor() : (Double, Double) -> String {
    override fun invoke(amount: Double, rate: Double): String {
        val mAmount = amount.toBigDecimal()
        val mRate = rate.toBigDecimal()
        val result = (mAmount * mRate)

        return when {
            result.inRange(0, 100) ->
                result.roundToDecimalPlaces(4)
                    .toPlainString()
                    .takeAfterDelimiter(4)
                    .addMissingZeros(4)

            result.inRange(100, 1_000_000) ->
                result.roundToDecimalPlaces(2)
                    .toPlainString()
                    .takeAfterDelimiter(2)
                    .addMissingZeros(2)

            else -> result.roundToDecimalPlaces(0).validateToString()
        }
    }

    private fun BigDecimal.roundToDecimalPlaces(scale: Int = 4): BigDecimal =
        this.setScale(scale, RoundingMode.DOWN)

    private fun String.takeAfterDelimiter(n: Int, delimiter: String = ".") =
        this.substringBefore(delimiter) + delimiter + this.substringAfter(delimiter).take(n)

    private fun String.addMissingZeros(precision: Int, delimiter: String = "."): String =
        if (this.substringAfter(delimiter).length < precision) this + 0 else this

    private fun BigDecimal.validateToString(): String =
        if (this > BigDecimal(Long.MAX_VALUE / 100)) "-1.00" else this.toPlainString()

    private fun BigDecimal.inRange(param1: Int, param2: Int): Boolean =
        this in BigDecimal(param1)..BigDecimal(param2)

}