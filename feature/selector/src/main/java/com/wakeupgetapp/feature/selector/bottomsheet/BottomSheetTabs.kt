package com.wakeupgetapp.feature.selector.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.wakeupgetapp.core.model.Currency
import com.wakeupgetapp.core.model.CustomExchange
import com.wakeupgetapp.feature.selector.R
import com.wakeupgetapp.feature.selector.bottomsheet.tabs.AllCurrenciesTab
import com.wakeupgetapp.feature.selector.bottomsheet.tabs.CustomExchangeTab
import com.wakeupgetapp.feature.selector.bottomsheet.tabs.RecentTab

@Composable
fun BottomSheetTabs(
    selectedTabIndex: MutableIntState,
    currencyList: List<Currency>,
    recentList: List<Currency>,
    customExchanges: List<CustomExchange>,
    currencySelectorText: MutableState<String>,
    addNewCurrencyExchange: (String, String, Double) -> Unit,
    editCurrencyExchange: (String, String, Double, Long) -> Unit,
    deleteCurrencyExchange: (Long) -> Unit,
    setCurrency: (String) -> Unit,
    currencyCodeWidth: Dp,
    setCustomExchange: (Long) -> Unit,
    keyboardHeight: Dp,
    navigationBarsPadding: Dp,
) {
    Column {
        TabRow(modifier = Modifier,
            selectedTabIndex = selectedTabIndex.intValue,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex.intValue]),
                    color = Color.White
                )
            },
            tabs = {
                SingleTab(selectedTabIndex, stringResource(id = R.string.tab_recent), 0)
                SingleTab(selectedTabIndex, stringResource(id = R.string.tab_custom), 1)
                SingleTab(selectedTabIndex, stringResource(id = R.string.tab_all), 2)
            })

        when (selectedTabIndex.intValue) {
            0 -> RecentTab(
                currencyList = recentList,
                currencyCodeWidth = currencyCodeWidth,
                setCurrency = setCurrency,
                bottomPadding = navigationBarsPadding
            )

            1 -> CustomExchangeTab(
                customExchanges = customExchanges,
                addCurrencyExchange = addNewCurrencyExchange,
                editCurrencyExchange = editCurrencyExchange,
                deleteCurrencyExchange = deleteCurrencyExchange,
                setCustomExchange = setCustomExchange,
                bottomPadding = navigationBarsPadding
            )

            2 -> AllCurrenciesTab(
                currencyList = currencyList,
                currencyCodeWidth = currencyCodeWidth,
                setCurrency = setCurrency,
                currencySelectorText = currencySelectorText,
                bottomPadding = keyboardHeight + navigationBarsPadding
            )
        }
    }
}