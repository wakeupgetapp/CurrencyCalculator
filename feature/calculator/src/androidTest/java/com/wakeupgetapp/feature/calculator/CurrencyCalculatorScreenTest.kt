package com.wakeupgetapp.feature.calculator

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.wakeupgetapp.core.model.Currency
import com.wakeupgetapp.core.model.CustomExchange
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CurrencyCalculatorScreenTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var emptyBaseHint: String
    private lateinit var emptyTargetHint: String
    private lateinit var emptyBaseAmount: String

    @Before
    fun setup() {
        rule.activity.apply {
            emptyBaseHint = rule.activity.getString(R.string.base_empty_hint)
            emptyTargetHint = rule.activity.getString(R.string.exchange_empty_hint)
            emptyBaseAmount = rule.activity.getString(R.string.start_typing)
        }
    }


    @Test
    fun screen_empty_data() = runTest {
        setupCalculatorScreen()
        rule.onNode(hasAnyChild(hasText("USD"))).assertIsDisplayed()
        rule.onNode(hasAnyChild(hasText("EUR"))).assertIsDisplayed()
        rule.onNode(hasAnyChild(hasText(emptyTargetHint))).assertIsDisplayed()
        rule.onNode(hasAnyChild(hasText(emptyTargetHint))).assertIsDisplayed()
        rule.onNode(hasAnyChild(hasAnySibling(hasText("1")))).assertIsDisplayed()
    }

    @Test
    fun screen_user_selected_currencies_loaded() = runTest {
        setupCalculatorScreen(baseCurrency = "PLN", targetCurrency = "AED")
        rule.onNode(hasAnyChild(hasText("PLN"))).assertIsDisplayed()
        rule.onNode(hasAnyChild(hasText("AED"))).assertIsDisplayed()
        rule.onNode(hasAnyChild(hasText(emptyBaseAmount))).assertIsDisplayed()
        rule.onNode(hasAnyChild(hasAnySibling(hasText("1")))).assertIsDisplayed()
    }

    private fun setupCalculatorScreen(
        baseCurrency: String = "",
        targetCurrency: String = "",
        baseAmount: String = "",
        exchangeAmount: String = "",
        exchangeRate: String = "",
        currencyList: List<Currency> = emptyList(),
        recentList: List<Currency> = emptyList(),
        customExchanges: List<CustomExchange> = emptyList(),
        goToRecent: Boolean = false,
        switchCurrencies: () -> Unit = {},
        setFilter: (String) -> Unit = {},
        openCurrencySelectorBase: () -> Unit = {},
        openCurrencySelectorExchange: () -> Unit = {},
        setCurrency: (String) -> Unit = {},
        resetCurrencySelector: () -> Unit = {},
        showCurrencySelector: Boolean = false,
        addNewCurrencyExchange: (String, String, Double) -> Unit = { _: String, _: String, _: Double -> },
        editCurrencyExchange: (String, String, Double, Long) -> Unit = { _: String, _: String, _: Double, _: Long -> },
        deleteCurrencyExchange: (Long) -> Unit = {},
        setCurrencyCustomExchange: (Long) -> Unit = {},
        keyboardOnClick: (String, Boolean) -> Unit = {_: String, _: Boolean ->},
        updateInfoComponent: @Composable () -> Unit = {},
    ) {
        rule.setContent {
            CurrencyCalculatorScreen(
                baseCurrency = baseCurrency,
                targetCurrency = targetCurrency,
                baseAmount = baseAmount,
                exchangeAmount = exchangeAmount,
                exchangeRate = exchangeRate,
                currencyList = currencyList,
                recentList = recentList,
                customExchanges = customExchanges,
                goToRecent = goToRecent,
                switchCurrencies = switchCurrencies,
                setFilter = setFilter,
                openCurrencySelectorBase = openCurrencySelectorBase,
                openCurrencySelectorExchange = openCurrencySelectorExchange,
                setCurrency = setCurrency,
                resetCurrencySelector = resetCurrencySelector,
                showCurrencySelector = showCurrencySelector,
                addNewCurrencyExchange = addNewCurrencyExchange,
                editCurrencyExchange = editCurrencyExchange,
                deleteCurrencyExchange = deleteCurrencyExchange,
                setCurrencyCustomExchange = setCurrencyCustomExchange,
                keyboardOnClick = keyboardOnClick,
                updateInfoComponent = updateInfoComponent,
            )
        }
    }
}