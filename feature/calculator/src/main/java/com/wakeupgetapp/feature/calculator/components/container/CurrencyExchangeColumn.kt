package com.wakeupgetapp.feature.calculator.components.container

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wakeupgetapp.core.ui.common.util.PlaceComponentWithMeasuredWidth
import com.wakeupgetapp.feature.calculator.R

@Composable
fun CurrencyExchangeColumn(
    modifier: Modifier = Modifier,
    currencyBaseName: String,
    currencyTargetName: String,
    baseAmount: String,
    targetAmount: String,
    animateCurrencyContainer: Boolean,
    currencyBaseOnClick: () -> Unit,
    currencyToExchangeOnClick: () -> Unit
) {
    val baseAmountHint =
        if (currencyBaseName.isBlank()) stringResource(id = R.string.base_empty_hint)
        else if (currencyBaseName.isNotBlank() && currencyTargetName.isNotBlank())
            stringResource(id = R.string.start_typing) else " "

    val targetAmountHint = if (currencyTargetName.isBlank())
        stringResource(id = R.string.exchange_empty_hint) else " "

    val targetAmountText =
        if (baseAmount.isBlank()) ""
        else if (baseAmount.toDoubleOrNull() != 0.0 && targetAmount.toDoubleOrNull() == 0.0)
            stringResource(id = R.string.amount_too_small)
        else if (targetAmount == "-1.00") stringResource(id = R.string.amount_too_big)
        else targetAmount

    PlaceComponentWithMeasuredWidth(
        viewToMeasure = {
            WidestCurrencyName(currencyBaseName, currencyTargetName)
        }, content = { currencySelectorTextWidth ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            ) {
                CurrencyRow(
                    currencyName = currencyBaseName,
                    currencyHint = "USD",
                    amount = baseAmount,
                    amountHint = baseAmountHint,
                    animateContainer = animateCurrencyContainer,
                    currencyNameOnClick = currencyBaseOnClick,
                    currencySelectorTextWidth = currencySelectorTextWidth,
                )


                Icon(
                    modifier = modifier
                        .padding(8.dp)
                        .size(48.dp)
                        .aspectRatio(1f)
                        .rotate(90f),
                    imageVector = Icons.AutoMirrored.Default.ArrowForward,
                    contentDescription = stringResource(
                        id = R.string.is_string
                    ),
                    tint = Color.White
                )

                CurrencyRow(
                    currencyName = currencyTargetName,
                    currencyHint = "EUR",
                    amount = targetAmountText,
                    amountHint = targetAmountHint,
                    animateContainer = animateCurrencyContainer,
                    currencyNameOnClick = currencyToExchangeOnClick,
                    currencySelectorTextWidth = currencySelectorTextWidth
                )
            }
        }
    )
}


@Composable
private fun WidestCurrencyName(currencyBaseName: String, currencyToExchangeName: String) {
    Box(modifier = Modifier.wrapContentSize()) {
        Text(text = currencyBaseName.ifBlank { "USD" }, fontSize = 30.sp)
        Text(text = currencyToExchangeName.ifBlank { "EUR" }, fontSize = 30.sp)
    }
}