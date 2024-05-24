package com.wakeupgetapp.core.domain

import com.wakeupgetapp.core.model.Currency
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FilterCurrenciesUseCaseTest {

    private val subject = FilterCurrenciesUseCase()

    private val currencyList = listOf<Currency>(
        Currency("US Dollar", "USD", "$", 1.0),
        Currency("Euro", "EUR", "€", 0.85),
        Currency("British Pound", "GBP", "£", 0.73),
        Currency("Japanese Yen", "JPY", "¥", 109.34),
    )

    @Test
    fun filtered_list_contains_all_currencies_with_no_filter() = runTest {
        assertEquals(currencyList, subject(currencyList, ""))
    }

    @Test
    fun filtered_list_contains_only_filtered_currencies() = runTest {
        val filter = "U"
        val list = currencyList.filter {
            it.code.contains(filter, ignoreCase = true) || it.name.contains(
                filter,
                ignoreCase = true
            )
        }

        assertEquals(list, subject(currencyList, filter))
    }

    @Test
    fun filter_filters_by_currency_name() {
        val filter = "US Doll"
        assertEquals(
            listOf(Currency("US Dollar", "USD", "$", 1.0)),
            subject(currencyList, filter)
        )
    }

    @Test
    fun filter_filters_by_code_name() {
        val filter = "USD"
        assertEquals(
            listOf(Currency("US Dollar", "USD", "$", 1.0)),
            subject(currencyList, filter)
        )
    }

    @Test
    fun filter_no_matching_returns_empty_currency_list() = runTest {
        val filter = "USDSD"
        assertEquals(emptyList<Currency>(), subject(currencyList, filter))
    }
}