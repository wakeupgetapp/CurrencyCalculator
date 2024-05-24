package com.wakeupgetapp.core.domain

import com.wakeupgetapp.core.data.repository.CurrencyRepository
import com.wakeupgetapp.core.data.repository.CurrencyUpdateRepository
import com.wakeupgetapp.core.model.CurrencyUpdateState
import com.wakeupgetapp.network.model.CallResult
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkConstructor
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class CurrencyUpdateUseCaseTest {

    private lateinit var currencyRepository: CurrencyRepository
    private lateinit var updateRepository: CurrencyUpdateRepository
    private lateinit var subject: CurrencyUpdateUseCase

    @Before
    fun setup() {
        currencyRepository = mockk<CurrencyRepository>()
        updateRepository = mockk<CurrencyUpdateRepository>()
        subject = CurrencyUpdateUseCase(updateRepository, currencyRepository)
    }

    @Test
    fun base_link_returns_success_correct_order() = runTest {
        coEvery { currencyRepository.updateCurrencies(any())} just Runs
        coEvery { updateRepository.fetchLatestData() } answers { CallResult.Success(mockk()) }
        subject().collect {}

        coVerifyOrder {
            updateRepository.fetchLatestData()
            currencyRepository.updateCurrencies(any())
        }
    }

    @Test
    fun base_link_returns_success_correct_result_order() = runTest {
        coEvery { updateRepository.fetchLatestData() } answers { CallResult.Success(mockk()) }
        coEvery { currencyRepository.updateCurrencies(any())} just Runs
        val results = subject().toList()
        assertEquals(listOf(CurrencyUpdateState.Loading, CurrencyUpdateState.Success), results)
    }

    @Test
    fun base_link_returns_failure_backup_returns_success() = runTest {
        coEvery { currencyRepository.updateCurrencies(any())} just Runs

        mockkConstructor(CallResult.Failure::class)

        coEvery { updateRepository.fetchLatestData() } answers { CallResult.Failure(null, null, null) }
        coEvery { updateRepository.fetchLatestDataBackup() } answers { CallResult.Success(mockk()) }
        assertEquals(CurrencyUpdateState.Success, subject().last())
    }

    @Test
    fun base_link_returns_failure_backup_returns_success_correct_order() = runTest {
        coEvery { updateRepository.fetchLatestData() } answers { CallResult.Failure(null, null, null) }
        coEvery { updateRepository.fetchLatestDataBackup() } answers { CallResult.Success(mockk()) }
        coEvery { currencyRepository.updateCurrencies(any())} just Runs

        subject().collect {}

        coVerifyOrder {
            updateRepository.fetchLatestData()
            updateRepository.fetchLatestDataBackup()
            currencyRepository.updateCurrencies(any())
        }
    }

    @Test
    fun base_link_returns_failure_backup_returns_success_correct_result_order() = runTest {
        coEvery { updateRepository.fetchLatestData() } answers { CallResult.Failure(null, null, null) }
        coEvery { updateRepository.fetchLatestDataBackup() } answers { CallResult.Success(mockk()) }
        coEvery { currencyRepository.updateCurrencies(any())} just Runs
        val results = subject().toList()
        assertEquals(listOf(CurrencyUpdateState.Loading, CurrencyUpdateState.Success), results)
    }

    @Test
    fun base_link_network_failure_backup_no_launched() = runTest {
        coEvery { updateRepository.fetchLatestData() } answers { CallResult.NetworkFailure }
        subject().collect {}
        coVerifyOrder {
            updateRepository.fetchLatestData()
        }
    }

    @Test
    fun base_link_network_failure_correct_results() = runTest {
        coEvery { updateRepository.fetchLatestData() } answers { CallResult.NetworkFailure }
        val results = subject().toList()
        assertEquals(listOf(CurrencyUpdateState.Loading, CurrencyUpdateState.CurrencyUpdateFailure), results)
    }

    @Test
    fun base_link_failure_backup_failure_correct_order() = runTest {
        coEvery { updateRepository.fetchLatestData() } answers { CallResult.Failure(null, null, null) }
        coEvery { updateRepository.fetchLatestDataBackup() } answers { CallResult.Failure(null, null, null) }
        subject().collect {}
        coVerifyOrder {
            updateRepository.fetchLatestData()
            updateRepository.fetchLatestDataBackup()
        }
    }

    @Test
    fun base_link_failure_backup_network_failure_correct_order() = runTest {
        coEvery { updateRepository.fetchLatestData() } answers { CallResult.Failure(null, null, null) }
        coEvery { updateRepository.fetchLatestDataBackup() } answers { CallResult.NetworkFailure }
        subject().collect {}
        coVerifyOrder {
            updateRepository.fetchLatestData()
            updateRepository.fetchLatestDataBackup()
        }
    }

    @Test
    fun base_link_failure_backup_failure_correct_results() = runTest {
        coEvery { updateRepository.fetchLatestData() } answers { CallResult.Failure(null, null, null) }
        coEvery { updateRepository.fetchLatestDataBackup() } answers { CallResult.Failure(null, null, null) }

        val result = subject().toList()

        assertEquals(listOf(CurrencyUpdateState.Loading, CurrencyUpdateState.Failure(null, null, null)), result)
    }

    @Test
    fun base_link_failure_backup_network_failure_correct_results() = runTest {
        coEvery { updateRepository.fetchLatestData() } answers { CallResult.Failure(null, null, null) }
        coEvery { updateRepository.fetchLatestDataBackup() } answers { CallResult.NetworkFailure }

        val result = subject().toList()

        assertEquals(listOf(CurrencyUpdateState.Loading, CurrencyUpdateState.CurrencyUpdateFailure), result)
    }
}