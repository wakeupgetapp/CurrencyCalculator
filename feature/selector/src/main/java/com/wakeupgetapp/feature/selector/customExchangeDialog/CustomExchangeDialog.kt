package com.wakeupgetapp.feature.selector.customExchangeDialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.wakeupgetapp.core.ui.common.CommonDarkButton
import com.wakeupgetapp.core.ui.common.CommonWhiteButton
import com.wakeupgetapp.core.ui.common.CurrencyBaseText
import com.wakeupgetapp.feature.selector.R


@Composable
fun CustomExchangeDialog(
    showDialog: MutableState<Boolean>,
    onDoneAction: (String, String, Double) -> Unit,
    baseCode: String = "",
    targetCode: String = "",
    rate: String = ""
) {
    if (showDialog.value) {
        Dialog(
            onDismissRequest = {
                showDialog.value = false
            }, properties = DialogProperties(
                dismissOnClickOutside = true, dismissOnBackPress = true
            )
        ) {
            DialogContent(
                baseCode = baseCode,
                targetCode = targetCode,
                exchangeRate = rate,
                addNewCurrencyExchange = { base: String, target: String, rate: Double ->
                    onDoneAction(base, target, rate)
                    showDialog.value = false
                })
        }
    }
}

@Composable
fun DialogContent(
    baseCode: String,
    targetCode: String,
    exchangeRate: String,
    addNewCurrencyExchange: (String, String, Double) -> Unit,
) {
    val baseCurrencyName = remember { mutableStateOf(baseCode) }
    val targetCurrencyName = remember { mutableStateOf(targetCode) }
    val rate = remember { mutableStateOf(exchangeRate) }
    val rateValid by remember { derivedStateOf { isRateValid(rate.value) } }

    val onDone = {
        if (rateValid)
            addNewCurrencyExchange(
                baseCurrencyName.value,
                targetCurrencyName.value,
                rate.value.toDouble()
            )
    }
    val currenciesSubmitted =
        (baseCurrencyName.value.isNotBlank() && targetCurrencyName.value.isNotBlank())

    Surface(
        shape = MaterialTheme.shapes.extraLarge,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(
                top = 24.dp, bottom = 24.dp, start = 24.dp, end = 24.dp
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CurrencyBaseText(
                text = if (currenciesSubmitted)
                    stringResource(id = R.string.specify_rate)
                else stringResource(id = R.string.specify_names),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp),
                color = Color.Black
            )

            HorizontalDivider(color = Color.DarkGray)

            CurrencyNameColumn(baseCurrencyName, targetCurrencyName)

            if (currenciesSubmitted) {
                RateColumn(
                    firstCurrencyName = baseCurrencyName.value,
                    secondCurrencyName = targetCurrencyName.value,
                    amount = rate.value,
                    onDone = onDone,
                    onValueChange = { it: String ->
                        val s = it.replace(",", ".").trim()
                        rate.value = handleRateInput(currentValue = rate.value, newValue = s)
                    }
                )

                if (rateValid)
                    CommonDarkButton(
                        modifier = Modifier
                            .wrapContentSize(Alignment.BottomCenter),
                        text = stringResource(id = R.string.done),
                        icon = Icons.Default.Check,
                        onClick = { onDone() })
                else
                    CommonWhiteButton(
                        modifier = Modifier
                            .wrapContentSize(Alignment.BottomCenter),
                        text = stringResource(id = R.string.done),
                        textColor = Color.Black,
                        icon = Icons.Default.Clear,
                        iconColor = Color.Black,
                        onClick = { })
            }
        }
    }
}


private fun handleRateInput(currentValue: String, newValue: String): String {
    if (newValue.isBlank()) return newValue
    if (newValue.toDoubleOrNull() == null) return currentValue
    if (newValue.toDouble() < 0) return currentValue

    val decimalIndex = newValue.indexOf('.')
    return if (decimalIndex != -1 && decimalIndex < 5 && newValue.length - decimalIndex - 1 < 5
        || (decimalIndex == -1 && newValue.length < 5)
    ) newValue else currentValue
}

private fun isRateValid(rate: String): Boolean {
    if (rate.isBlank()) return false
    if (rate.toDoubleOrNull() == null) return false
    if (rate.toDouble() <= 0) return false
    return true
}