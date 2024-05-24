package com.wakeupgetapp.feature.selector.bottomsheet.tabs

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wakeupgetapp.core.model.Currency
import com.wakeupgetapp.feature.selector.R
import com.wakeupgetapp.feature.selector.bottomsheet.CurrencySelectorButton
import com.wakeupgetapp.feature.selector.bottomsheet.EmptyTab


@Composable
fun RecentTab(
    currencyList: List<Currency>,
    currencyCodeWidth: Dp,
    setCurrency: (String) -> Unit,
    bottomPadding: Dp
) {

    if (currencyList.isEmpty()) {
        EmptyTab(text = stringResource(id = R.string.empty_recent_tab))
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
        ) {
            items(currencyList) {
                CurrencySelectorButton(it, currencyCodeWidth, setCurrency)
            }

            item {
                Spacer(modifier = Modifier.size(bottomPadding))
            }
        }
    }
}