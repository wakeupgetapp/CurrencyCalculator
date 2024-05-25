package com.wakeupgetapp.feature.calculator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wakeupgetapp.core.model.CurrencyUpdateState
import com.wakeupgetapp.feature.calculator.components.updateCurrencyInfoBar.RatesInfoComponent

@Composable
fun CurrencyCalculatorRoute(
    viewmodel: CurrencyCalculatorViewModel = hiltViewModel()
) {
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle(
        initialValue = UiState(
            DataState.Loading,
            BottomSheetData(),
            CurrencyUpdateState.Loading,
            SelectedCurrencies(),
            CurrencySelectorTarget.NONE
        )
    )

    when (val uiDataState = uiState.dataState) {
        is DataState.Loaded -> {
            CurrencyCalculatorScreen(
                baseCurrency = uiState.selectedCurrencies.baseCode,
                targetCurrency = uiState.selectedCurrencies.targetCode,
                baseAmount = uiState.selectedCurrencies.baseAmount,
                exchangeAmount = uiState.selectedCurrencies.exchangeAmount,
                exchangeRate = uiState.selectedCurrencies.rate,
                animateCurrencyContainer = uiState.selectedCurrencies.animateContainer,
                currencyList = uiState.bottomSheetData.filteredList,
                recentList = uiState.bottomSheetData.recentList,
                customExchanges = uiDataState.customExchangeList,
                goToRecent = uiState.bottomSheetData.goToRecent,
                switchCurrencies = { viewmodel.switchCurrencies() },
                setFilter = { filter: String -> viewmodel.setFilter(filter) },
                showCurrencySelector = uiState.currencySelectorTarget != CurrencySelectorTarget.NONE,
                openCurrencySelectorBase = { viewmodel.setCurrencyTarget(CurrencySelectorTarget.BASE) },
                openCurrencySelectorExchange = { viewmodel.setCurrencyTarget(CurrencySelectorTarget.TARGET) },
                setCurrency = { it: String -> viewmodel.setCurrency(it) },
                resetCurrencySelector = {
                    viewmodel.setCurrencyTarget(CurrencySelectorTarget.NONE)
                    viewmodel.setFilter("")
                },
                addNewCurrencyExchange = { base: String, target: String, rate: Double ->
                    viewmodel.addCurrencyExchange(base, target, rate)
                },
                editCurrencyExchange = { base: String, target: String, rate: Double, id: Long ->
                    viewmodel.editCurrencyExchange(base, target, rate, id)
                },
                deleteCurrencyExchange = { id: Long -> viewmodel.deleteCurrencyExchange(id) },
                setCurrencyCustomExchange = { id: Long ->
                    viewmodel.setCustomExchange(id)
                },
                keyboardOnClick = { input: String, longPress: Boolean ->
                    viewmodel.handleKeyboardInput(input, longPress)
                },
                updateInfoComponent = {
                    RatesInfoComponent(
                        currencyUpdateState = uiState.currencyUpdateState,
                        tryAgainButton = viewmodel::updateCurrencies
                    )
                }
            )
        }

        else -> PreparingDataScreen(
            uiState = uiState,
            viewmodel::updateCurrencies
        )
    }


}
