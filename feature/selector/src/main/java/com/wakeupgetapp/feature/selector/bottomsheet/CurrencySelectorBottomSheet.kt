package com.wakeupgetapp.feature.selector.bottomsheet

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wakeupgetapp.core.model.Currency
import com.wakeupgetapp.core.model.CustomExchange
import com.wakeupgetapp.core.ui.common.util.calculateSystemBottomPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencySelectorBottomSheet(
    showBottomSheet: Boolean,
    resetCurrencySelector: () -> Unit,
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
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val navigationBarsPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    val systemBarsPadding by calculateSystemBottomPadding()

    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxHeight(0.8f),
            onDismissRequest = {
                resetCurrencySelector()
            },
            sheetState = sheetState,
            dragHandle = { /* no-op */ },
            windowInsets = WindowInsets(bottom = 0.dp)
        ) {

            BottomSheetContent(
                currencyList = currencyList,
                recentList = recentList,
                customExchanges = customExchanges,
                setFilter = setFilter,
                setCurrency = setCurrency,
                goToRecent = goToRecent,
                addNewCurrencyExchange = addNewCurrencyExchange,
                editCurrencyExchange = editCurrencyExchange,
                deleteCurrencyExchange = deleteCurrencyExchange,
                setCurrencyCustomExchange = setCurrencyCustomExchange,
                keyboardHeight = systemBarsPadding - navigationBarsPadding,
                navigationBarsPadding = navigationBarsPadding
            )
        }

    }
}