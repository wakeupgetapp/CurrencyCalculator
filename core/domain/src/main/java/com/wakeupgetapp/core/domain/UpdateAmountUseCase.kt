package com.wakeupgetapp.core.domain

import javax.inject.Inject

class UpdateAmountUseCase @Inject constructor() : (String, String, Boolean) -> String {
    override fun invoke(input: String, amount: String, longClick: Boolean): String =
        when (input) {
            "<" -> if (longClick || amount == "0.") "" else amount.dropLast(1)
            "0" -> {
                if (amount.isBlank() || amount == "0") "0."
                else if (amount.length > 15) amount
                else amount + input
            }

            "." -> {
                if (amount.isBlank()) "0."
                else if (amount.contains(".") || amount.isBlank()) amount
                else amount + input
            }

            else -> if (amount.length > 15) amount else amount + input
        }
}