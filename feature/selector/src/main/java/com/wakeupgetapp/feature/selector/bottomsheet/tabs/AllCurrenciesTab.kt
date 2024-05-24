package com.wakeupgetapp.feature.selector.bottomsheet.tabs

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wakeupgetapp.core.model.Currency
import com.wakeupgetapp.feature.selector.bottomsheet.CurrencySearchBar
import com.wakeupgetapp.feature.selector.bottomsheet.CurrencySelectorButton

@Composable
fun AllCurrenciesTab(
    currencyList: List<Currency>,
    currencyCodeWidth: Dp,
    setCurrency: (String) -> Unit,
    currencySelectorText: MutableState<String>,
    bottomPadding: Dp
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        item {
            CurrencySearchBar(
                modifier = Modifier,
                text = currencySelectorText.value,
                onValueChange = { it: String -> currencySelectorText.value = it })
        }

        item {
            HorizontalDivider()
        }

        items(items = currencyList, key = {it.code}) {
            CurrencySelectorButton(it, currencyCodeWidth, setCurrency)
        }

        item {
            Spacer(modifier = Modifier.size(bottomPadding))
        }
    }
}