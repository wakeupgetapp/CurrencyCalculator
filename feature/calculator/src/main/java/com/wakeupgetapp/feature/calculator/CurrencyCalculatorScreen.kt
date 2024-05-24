package com.wakeupgetapp.feature.calculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wakeupgetapp.core.model.Currency
import com.wakeupgetapp.core.model.CustomExchange
import com.wakeupgetapp.core.ui.common.CurrencyBaseText
import com.wakeupgetapp.feature.calculator.components.container.CurrencyExchangeColumn
import com.wakeupgetapp.feature.calculator.components.CurrencyKeyboard
import com.wakeupgetapp.feature.selector.bottomsheet.CurrencySelectorBottomSheet

@Composable
fun CurrencyCalculatorScreen(
    baseCurrency: String,
    targetCurrency: String,
    baseAmount: String,
    exchangeAmount: String,
    exchangeRate: String,
    currencyList: List<Currency>,
    recentList: List<Currency>,
    customExchanges: List<CustomExchange>,
    goToRecent: Boolean,
    switchCurrencies: () -> Unit,
    setFilter: (String) -> Unit,
    openCurrencySelectorBase: () -> Unit,
    openCurrencySelectorExchange: () -> Unit,
    setCurrency: (String) -> Unit,
    resetCurrencySelector: () -> Unit,
    showCurrencySelector: Boolean,
    addNewCurrencyExchange: (String, String, Double) -> Unit,
    editCurrencyExchange: (String, String, Double, Long) -> Unit,
    deleteCurrencyExchange: (Long) -> Unit,
    setCurrencyCustomExchange: (Long) -> Unit,
    keyboardOnClick: (String, Boolean) -> Unit,
    updateInfoComponent: @Composable () -> Unit,
) {
    CurrencySelectorBottomSheet(
        showBottomSheet = showCurrencySelector,
        resetCurrencySelector = resetCurrencySelector,
        currencyList = currencyList,
        recentList = recentList,
        customExchanges = customExchanges,
        setFilter = setFilter,
        setCurrency = setCurrency,
        goToRecent = goToRecent,
        addNewCurrencyExchange = addNewCurrencyExchange,
        editCurrencyExchange = editCurrencyExchange,
        deleteCurrencyExchange = deleteCurrencyExchange,
        setCurrencyCustomExchange = setCurrencyCustomExchange
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 0.dp, bottom = 0.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Column(
            modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Bottom
        ) {
            CurrencyExchangeColumn(
                currencyBaseName = baseCurrency,
                currencyTargetName = targetCurrency,
                baseAmount = baseAmount,
                targetAmount = exchangeAmount,
                currencyBaseOnClick = openCurrencySelectorBase,
                currencyToExchangeOnClick = openCurrencySelectorExchange
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                Arrangement.Absolute.SpaceAround
            ) {
                if (baseCurrency.isNotBlank() && targetCurrency.isNotBlank()) {
                    CurrencyBaseText(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        text = "1 $baseCurrency =\n$exchangeRate $targetCurrency",
                        color = Color.White
                    )

                    IconButton(modifier = Modifier.align(Alignment.CenterVertically),
                        onClick = { switchCurrencies() }) {
                        Icon(
                            painterResource(id = R.drawable.currency_swap),
                            stringResource(id = R.string.swap_currencies),
                            tint = Color.White
                        )
                    }
                } else {
                    IconButton(modifier = Modifier.align(Alignment.CenterVertically),
                        enabled = false,
                        onClick = {}) {
                        Icon(
                            painterResource(id = R.drawable.currency_swap),
                            stringResource(id = R.string.swap_currencies),
                            tint = Color.Transparent
                        )
                    }
                }
            }
        }

        Column(
            Modifier.weight(1f, fill = true), verticalArrangement = Arrangement.Bottom
        ) {
            updateInfoComponent()
            Spacer(modifier = Modifier.size(8.dp))
            CurrencyKeyboard(
                modifier = Modifier.fillMaxWidth(), onClick = keyboardOnClick
            )
        }

    }
}