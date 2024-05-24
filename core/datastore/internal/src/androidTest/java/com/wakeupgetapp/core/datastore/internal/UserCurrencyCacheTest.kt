package com.wakeupgetapp.core.datastore.internal

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.wakeupgetapp.core.data.repository.UserCurrencyCache
import com.wakeupgetapp.core.datastore.internal.repository.UserCurrencyCacheImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

@OptIn(ExperimentalCoroutinesApi::class)
class UserCurrencyCacheTest {

    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var subject: UserCurrencyCache

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @Before
    fun setup() {
        subject = UserCurrencyCacheImpl(
            tmpFolder.testUserPreferencesDataStore(testScope),
        )
    }

    @Test
    fun emptyDataStore_return_default_values() = runTest {
        assertEquals(subject.readBaseCurrency.first(), "")
        assertEquals(subject.readTargetCurrency.first(), "")
        assertEquals(subject.customExchangeId.first(), -1L)
    }

    @Test
    fun base_currency_save_and_return() = runTest {
        val currency = "USD"
        subject.saveBaseCurrency(currency)
        assertEquals(subject.readBaseCurrency.first(), currency)
    }

    @Test
    fun target_currency_save_and_return() = runTest {
        val currency = "EUR"
        subject.saveTargetCurrency(currency)
        assertEquals(subject.readTargetCurrency.first(), currency)
    }

    @Test
    fun customExchangeId_save_and_return() = runTest {
        val id = 17L
        subject.setCustomExchangeId(id)
        assertEquals(subject.customExchangeId.first(), id)
    }

}

fun TemporaryFolder.testUserPreferencesDataStore(
    coroutineScope: CoroutineScope
) = PreferenceDataStoreFactory.create(
    scope = coroutineScope,
) {
    newFile("test.preferences_pb")
}
