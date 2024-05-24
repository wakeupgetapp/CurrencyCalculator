package com.wakeupgetapp.feature.calculator

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.wakeupgetapp.core.data_test.repository.FakeCurrencyRepository
import com.wakeupgetapp.core.data_test.repository.FakeCurrencyUpdateRepository
import com.wakeupgetapp.core.data_test.repository.FakeUserCurrencyCache
import com.wakeupgetapp.core.domain.CalculateTargetAmountUseCase
import com.wakeupgetapp.core.domain.CurrencyUpdateUseCase
import com.wakeupgetapp.core.domain.FilterCurrenciesUseCase
import com.wakeupgetapp.core.domain.NormalizeRateUseCase
import com.wakeupgetapp.core.domain.UpdateAmountUseCase
import com.wakeupgetapp.core.model.Currency
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CurrencyCalculatorRouteTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    private val currencyRepository = FakeCurrencyRepository()
    private val currencyUpdateRepository = FakeCurrencyUpdateRepository()
    private val userCurrencyCache = FakeUserCurrencyCache()
    private val currencyUpdateUseCase =
        CurrencyUpdateUseCase(currencyUpdateRepository, currencyRepository)
    private val normalizeRate = NormalizeRateUseCase()
    private val calculateTargetAmount = CalculateTargetAmountUseCase()
    private val updateAmountUseCase = UpdateAmountUseCase()
    private val filterCurrencies = FilterCurrenciesUseCase()
    private lateinit var viewModel: CurrencyCalculatorViewModel

    private fun setupViewModel() {
        viewModel = CurrencyCalculatorViewModel(
            currencyRepository,
            userCurrencyCache,
            currencyUpdateUseCase,
            normalizeRate,
            calculateTargetAmount,
            filterCurrencies,
            updateAmountUseCase
        )
    }

    private lateinit var emptyBaseHint: String
    private lateinit var dialogNoInternetText: String

    @Before
    fun setup() {
        rule.activity.apply {
            emptyBaseHint = rule.activity.getString(R.string.base_empty_hint)
            dialogNoInternetText = rule.activity.getString(R.string.init_api_error)
        }
    }

    @Test
    fun currencies_empty_no_internet_dialog() = runTest {
        setupViewModel()
        rule.setContent {
            CurrencyCalculatorRoute(viewmodel = viewModel)
        }

        rule.onNode(hasAnyChild(hasText(dialogNoInternetText))).assertIsDisplayed()
    }

    @Test
    fun currencies_loaded_user_cache_empty() = runTest {
        currencyRepository.setInitialCurrencyList(currencyList)
        setupViewModel()
        rule.setContent {
            CurrencyCalculatorRoute(viewmodel = viewModel)
        }

        rule.onNode(hasAnyChild(hasText(emptyBaseHint))).assertIsDisplayed()
    }

    private val currencyList = listOf<Currency>(
        Currency("US Dollar", "USD", "$", 1.0),
        Currency("Euro", "EUR", "€", 0.85),
        Currency("British Pound", "GBP", "£", 0.73),
        Currency("Japanese Yen", "JPY", "¥", 109.34),
    )
}