package com.wakeupgetapp.database.internal.currencycalculator

import com.wakeupgetapp.core.data.repository.CurrencyRepository
import com.wakeupgetapp.core.model.Currency
import com.wakeupgetapp.core.model.CurrencyTable
import com.wakeupgetapp.database.internal.currencycalculator.testdoubles.TestDoubleCurrencyDao
import com.wakeupgetapp.database.internal.currencycalculator.testdoubles.TestDoubleCustomExchangeDao
import com.wakeupgetapp.core.database.internal.repository.CurrencyRepositoryImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CurrencyRepositoryTest {

    private lateinit var repository: CurrencyRepository

    @Before
    fun setup() {
        repository = CurrencyRepositoryImpl(TestDoubleCurrencyDao(), TestDoubleCustomExchangeDao())
    }

    @Test
    fun empty_currencies_returns_empty_list() = runTest {
        assertEquals(listOf<Currency>(), repository.getCurrencies().first())
    }

    @Test
    fun upsert_currencies_rate_and_code_only_returns_list_with_updated_fields() = runTest {
        val list = listOf(
            Currency("", "USD", "", 1.0),
            Currency("", "EUR", "", 0.85),
            Currency("", "GBP", "", 0.73),
            Currency("", "JPY", "", 109.34)
        )

        repository.updateCurrencies(
            CurrencyTable(
                date = "01.01.00",
                currencyList = list
            )
        )

        val expectedList = listOf(
            Currency("US Dollar", "USD", "$", 1.0, 0),
            Currency("Euro", "EUR", "€", 0.85, 0),
            Currency("British Pound Sterling", "GBP", "£", 0.73, 0),
            Currency("Japanese Yen", "JPY", "¥", 109.34, 0)
        )

        assertEquals(expectedList, repository.getCurrencies().first())
    }

    @Test
    fun upsert_currencies_to_empty_return_list_with_timestamp() = runTest {
        val currencyList = listOf(
            Currency("US Dollar", "USD", "$", 1.0),
            Currency("Euro", "EUR", "€", 0.85),
            Currency("British Pound Sterling", "GBP", "£", 0.73),
            Currency("Japanese Yen", "JPY", "¥", 109.34)
        )

        repository.updateCurrencies(
            CurrencyTable(
                date = "01.01.00",
                currencyList = currencyList
            )
        )

        assertEquals(
            currencyList,
            repository.getCurrencies().first().map { it.copy(timestamp = 0) })
    }

    @Test
    fun upsert_currencies_rate_updates_rate() = runTest {
        val expectedList = listOf(
            Currency("US Dollar", "USD", "$", 1.0, 0),
            Currency("Euro", "EUR", "€", 0.85, 0),
            Currency("British Pound Sterling", "GBP", "£", 0.73, 0),
            Currency("Japanese Yen", "JPY", "¥", 109.34, 0)
        )

        val list = listOf(
            Currency("", "USD", "", 1.0),
            Currency("", "EUR", "", 0.85),
            Currency("", "GBP", "", 0.73),
            Currency("", "JPY", "", 109.34)
        )

        repository.updateCurrencies(
            CurrencyTable(
                date = "01.01.00",
                currencyList = list
            )
        )

        assertEquals(expectedList, repository.getCurrencies().first())

        val expectedList2 = listOf(
            Currency("US Dollar", "USD", "$", 2.0, 0),
            Currency("Euro", "EUR", "€", 2.85, 0),
            Currency("British Pound Sterling", "GBP", "£", 0.73, 0),
            Currency("Japanese Yen", "JPY", "¥", 109.34, 0)
        )

        val list2 = listOf(
            Currency("", "USD", "", 2.0),
            Currency("", "EUR", "", 2.85)
        )

        repository.updateCurrencies(
            CurrencyTable(
                date = "01.01.00",
                currencyList = list2
            )
        )

        assertEquals(expectedList2, repository.getCurrencies().first())
    }

    @Test
    fun update_currency_timestamp() = runTest {
        val expectedList = listOf(
            Currency("US Dollar", "USD", "$", 1.0, 0),
            Currency("Euro", "EUR", "€", 0.85, 0),
            Currency("British Pound Sterling", "GBP", "£", 0.73, 0),
            Currency("Japanese Yen", "JPY", "¥", 109.34, 0)
        )

        val list = listOf(
            Currency("", "USD", "", 1.0),
            Currency("", "EUR", "", 0.85),
            Currency("", "GBP", "", 0.73),
            Currency("", "JPY", "", 109.34)
        )

        repository.updateCurrencies(
            CurrencyTable(
                date = "01.01.00",
                currencyList = list
            )
        )

        assertEquals(expectedList, repository.getCurrencies().first())

        val expectedList2 = listOf(
            Currency("US Dollar", "USD", "$", 1.0, 13),
            Currency("Euro", "EUR", "€", 0.85, 0),
            Currency("British Pound Sterling", "GBP", "£", 0.73, 0),
            Currency("Japanese Yen", "JPY", "¥", 109.34, 0)
        )

        repository.updateCurrencyTimestamp(code = "USD", timestamp = 13)

        assertEquals(expectedList2, repository.getCurrencies().first())
    }
}