package com.wakeupgetapp.feature.selector.bottomsheet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.wakeupgetapp.core.model.Currency
import com.wakeupgetapp.core.model.CustomExchange
import com.wakeupgetapp.core.ui.common.util.PlaceComponentWithMeasuredWidth
import com.wakeupgetapp.core.ui.common.util.WidestCurrencyCodeText

@Composable
fun BottomSheetContent(
    currencyList: List<Currency>,
    recentList: List<Currency>,
    customExchanges: List<CustomExchange>,
    setFilter: (String) -> Unit,
    setCurrency: (String) -> Unit,
    goToRecent: Boolean,
    addNewCurrencyExchange: (String, String, Double) -> Unit,
    editCurrencyExchange: (String, String, Double, Long) -> Unit,
    deleteCurrencyExchange: (Long) -> Unit,
    setCurrencyCustomExchange: (Long) -> Unit,
    keyboardHeight: Dp,
    navigationBarsPadding: Dp,

    ) {
    val selectedTabIndex = remember { mutableIntStateOf(if (goToRecent) 0 else 2) }
    val currencySelectorText = remember { mutableStateOf("") }

    LaunchedEffect(selectedTabIndex.intValue, currencySelectorText.value) {
        when (selectedTabIndex.intValue) {
            0, 1 -> currencySelectorText.value = ""
            2 -> setFilter(currencySelectorText.value)
        }
    }

    PlaceComponentWithMeasuredWidth(
        viewToMeasure = { WidestCurrencyCodeText(fontSize = 24.sp) },
        content = {
            BottomSheetTabs(
                selectedTabIndex = selectedTabIndex,
                currencyList = currencyList,
                recentList = recentList,
                customExchanges = customExchanges,
                currencySelectorText = currencySelectorText,
                addNewCurrencyExchange = addNewCurrencyExchange,
                editCurrencyExchange = editCurrencyExchange,
                deleteCurrencyExchange = deleteCurrencyExchange,
                currencyCodeWidth = it,
                setCurrency = setCurrency,
                setCustomExchange = setCurrencyCustomExchange,
                keyboardHeight = keyboardHeight,
                navigationBarsPadding = navigationBarsPadding
            )
        }
    )
}