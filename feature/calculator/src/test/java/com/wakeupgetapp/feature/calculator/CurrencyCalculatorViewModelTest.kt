package com.wakeupgetapp.feature.calculator

import com.wakeupgetapp.core.data_test.repository.FakeCurrencyRepository
import com.wakeupgetapp.core.data_test.repository.FakeCurrencyUpdateRepository
import com.wakeupgetapp.core.data_test.repository.FakeUserCurrencyCache
import com.wakeupgetapp.core.data_test.util.MainDispatcherRule
import com.wakeupgetapp.core.domain.CalculateTargetAmountUseCase
import com.wakeupgetapp.core.domain.CurrencyUpdateUseCase
import com.wakeupgetapp.core.domain.FilterCurrenciesUseCase
import com.wakeupgetapp.core.domain.NormalizeRateUseCase
import com.wakeupgetapp.core.domain.UpdateAmountUseCase
import com.wakeupgetapp.core.model.Currency
import com.wakeupgetapp.core.model.CurrencyTable
import com.wakeupgetapp.core.model.CurrencyUpdateState
import com.wakeupgetapp.core.model.CustomExchange
import com.wakeupgetapp.core.network.model.CallResult
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class CurrencyCalculatorViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

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

    @Before
    fun setup() {
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

    @Test
    fun repository_returns_empty_list_data_state_is_empty() = runTest {
        currencyRepository.setInitialCurrencyList(listOf())
        assertEquals(DataState.Empty, viewModel.uiState.value.dataState)
    }

    @Test
    fun repository_returns_currencyList_uiStateData_is_loaded() = runTest {
        currencyRepository.setInitialCurrencyList(currencyList)
        assertEquals(DataState.Loaded(currencyList, listOf()), viewModel.uiState.value.dataState)
    }

    @Test
    fun repository_returns_currencyList_customExchangeList_uiStateData_is_loaded() = runTest {
        currencyRepository.setInitialCurrencyList(currencyList)
        currencyRepository.setInitialCustomExchangeList(customExchangeList)
        assertEquals(
            DataState.Loaded(currencyList, customExchangeList),
            viewModel.uiState.value.dataState
        )
    }

    @Test
    fun repository_returns_empty_list_currencyUpdateUseCase_updates_it_uiStateData_is_loaded() =
        runTest {
            currencyUpdateRepository.setInitialFetchLatestDataResult(
                CallResult.Success(
                    currencyTable
                )
            )
            viewModel.updateCurrencies()
            assertEquals(
                DataState.Loaded(currencyList, listOf()),
                viewModel.uiState.value.dataState
            )
        }

    @Test
    fun repository_returns_list_with_less_than_3_timestamps_goToRecent_false() = runTest {
        currencyRepository.setInitialCurrencyList(currencyList)
        assertEquals(false, viewModel.uiState.value.bottomSheetData.goToRecent)
    }

    @Test
    fun repository_returns_list_with_more_than_3_timestamps_goToRecent_true() = runTest {
        currencyRepository.setInitialCurrencyList(currencyList.map { it.copy(timestamp = 3) })
        assertEquals(true, viewModel.uiState.value.bottomSheetData.goToRecent)
    }

    @Test
    fun repository_returns_list_with_no_timestamps_recent_list_empty() = runTest {
        currencyRepository.setInitialCurrencyList(currencyList)
        assertEquals(emptyList<Currency>(), viewModel.uiState.value.bottomSheetData.recentList)
    }

    @Test
    fun repository_returns_list_with_timestamps_recent_list_contains_it() = runTest {
        val list = currencyList.map { it.copy(timestamp = 3) }
        currencyRepository.setInitialCurrencyList(list)
        assertEquals(list, viewModel.uiState.value.bottomSheetData.recentList)
    }

    @Test
    fun filtered_list_contains_all_currencies_with_no_filter() = runTest {
        currencyRepository.setInitialCurrencyList(currencyList)
        assertEquals(currencyList, viewModel.uiState.value.bottomSheetData.filteredList)
    }

    @Test
    fun filtered_list_contains_only_filtered_currencies() = runTest {
        currencyRepository.setInitialCurrencyList(currencyList)

        val filter = "U"
        val list = currencyList.filter {
            it.code.contains(filter, ignoreCase = true) || it.name.contains(
                filter,
                ignoreCase = true
            )
        }

        viewModel.setFilter(filter)

        assertEquals(list, viewModel.uiState.value.bottomSheetData.filteredList)
    }

    @Test
    fun currency_update_state_returns_NetworkError() = runTest {
        currencyUpdateRepository.setInitialFetchLatestDataResult(CallResult.NetworkFailure)
        viewModel.updateCurrencies()
        assertEquals(
            CurrencyUpdateState.CurrencyUpdateFailure,
            viewModel.uiState.value.currencyUpdateState
        )
    }

    @Test
    fun currency_update_state_returns_Failure() = runTest {
        // init repo value is error
        assertEquals(
            CurrencyUpdateState.Failure(null, null, null),
            viewModel.uiState.value.currencyUpdateState
        )
    }

    @Test
    fun currency_cache_empty_user_state_empty() = runTest {
        assertEquals(
            SelectedCurrencies("", "", "", ""),
            viewModel.uiState.value.selectedCurrencies)
    }

    @Test
    fun currency_cache_empty_user_state() = runTest {
        assertEquals(
            SelectedCurrencies("", "", "", ""),
            viewModel.uiState.value.selectedCurrencies)
    }

    @Test
    fun currency_cache_only_base_select() = runTest {
        currencyRepository.setInitialCurrencyList(currencyList)
        viewModel.setCurrencyTarget(CurrencySelectorTarget.BASE)
        viewModel.setCurrency("USD")

        assertEquals(
            SelectedCurrencies("USD", "", "", ""),
            viewModel.uiState.value.selectedCurrencies
        )
    }

    @Test
    fun currency_cache_only_target_select() = runTest {
        currencyRepository.setInitialCurrencyList(currencyList)
        viewModel.setCurrencyTarget(CurrencySelectorTarget.TARGET)
        viewModel.setCurrency("USD")

        assertEquals(
            SelectedCurrencies("", "USD", "", ""),
            viewModel.uiState.value.selectedCurrencies
        )
    }

    @Test
    fun incompleteLoaded_not_changing_base_amount() = runTest {
        currencyRepository.setInitialCurrencyList(currencyList)
        viewModel.setCurrencyTarget(CurrencySelectorTarget.BASE)
        viewModel.setCurrency("USD")

        viewModel.handleKeyboardInput("1", false)

        assertEquals(
            SelectedCurrencies("USD", "", "", ""),
            viewModel.uiState.value.selectedCurrencies
        )
    }

    @Test
    fun currenciesLoaded_change_base_amount_available() = runTest {
        currencyRepository.setInitialCurrencyList(currencyList)
        viewModel.setCurrencyTarget(CurrencySelectorTarget.BASE)
        viewModel.setCurrency("USD")
        viewModel.setCurrencyTarget(CurrencySelectorTarget.TARGET)
        viewModel.setCurrency("EUR")

        viewModel.handleKeyboardInput("1", false)

        assertEquals(
            SelectedCurrencies("USD", "EUR", "1", "0.8500", "0.85"),
            viewModel.uiState.value.selectedCurrencies
        )
    }

    @Test
    fun switch_TRUE_change_base_changing_target_currency() = runTest {
        currencyRepository.setInitialCurrencyList(currencyList)
        viewModel.switchCurrencies()
        viewModel.setCurrencyTarget(CurrencySelectorTarget.BASE)
        viewModel.setCurrency("USD")

        assertEquals(
            SelectedCurrencies("", "USD", "", "", ""),
            viewModel.uiState.value.selectedCurrencies
        )
    }

    @Test
    fun switch_TRUE_change_target_changing_base_currency() = runTest {
        currencyRepository.setInitialCurrencyList(currencyList)
        viewModel.switchCurrencies()
        viewModel.setCurrencyTarget(CurrencySelectorTarget.TARGET)
        viewModel.setCurrency("USD")

        assertEquals(
            SelectedCurrencies("USD", "", "", "", ""),
            viewModel.uiState.value.selectedCurrencies
        )
    }

    @Test
    fun switch_TRUE_recalculates_SelectedCurrencies() = runTest {
        currencyRepository.setInitialCurrencyList(currencyList)
        viewModel.setCurrencyTarget(CurrencySelectorTarget.BASE)
        viewModel.setCurrency("USD")
        viewModel.setCurrencyTarget(CurrencySelectorTarget.TARGET)
        viewModel.setCurrency("EUR")

        viewModel.handleKeyboardInput("1", false)

        viewModel.switchCurrencies()

        assertEquals(
            SelectedCurrencies("EUR", "USD", "1", "1.1765", "1.1765"),
            viewModel.uiState.value.selectedCurrencies
        )
    }

    private val currencyList = listOf<Currency>(
        Currency("US Dollar", "USD", "$", 1.0),
        Currency("Euro", "EUR", "€", 0.85),
        Currency("British Pound", "GBP", "£", 0.73),
        Currency("Japanese Yen", "JPY", "¥", 109.34),
    )

    private val customExchangeList = listOf<CustomExchange>(
        CustomExchange(1L, 0, "USD", "EUR", 0.85),
        CustomExchange(2L, 0, "USD", "GBP", 0.73),
    )

    private val currencyTable = CurrencyTable(
        date = "01.01.2000",
        currencyList = currencyList
    )
}
