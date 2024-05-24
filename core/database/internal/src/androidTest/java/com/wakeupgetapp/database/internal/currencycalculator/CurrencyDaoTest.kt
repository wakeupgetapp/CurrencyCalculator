package com.wakeupgetapp.database.internal.currencycalculator

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.wakeupgetapp.core.database.internal.CurrencyDatabase
import com.wakeupgetapp.core.database.internal.dao.CurrencyDao
import com.wakeupgetapp.core.database.internal.model.CurrencyEntity
import com.wakeupgetapp.core.database.internal.model.CurrencyTimestampEntity
import com.wakeupgetapp.core.database.internal.model.CurrencyWithTimestamp
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class CurrencyDaoTest {

    private lateinit var currencyDao: CurrencyDao
    private lateinit var db: CurrencyDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            CurrencyDatabase::class.java,
        ).build()
        currencyDao = db.currencyDao()
    }

    @After
    fun closeDb() = db.close()

    @Test
    fun save_currencies_returns_null_timestamps() = runTest {
        val currencyList = listOf(
            CurrencyEntity(code = "USD", rate = 1.0),
            CurrencyEntity(code = "EUR", rate = 0.85),
            CurrencyEntity(code = "JPY", rate = 110.53),
            CurrencyEntity(code = "GBP", rate = 0.72),
            CurrencyEntity(code = "AUD", rate = 1.34)
        )

        currencyDao.upsertCurrencyEntities(currencyList)

        val fetchedCurrencies = currencyDao.getCurrencies().first()

        assertEquals(
            currencyList.map { it.toCurrencyWithTimestamp(null) },
            fetchedCurrencies
        )
    }

    @Test
    fun update_currencyWithTimestamps_returns_correct_CurrencyWithTimestamp() = runTest {
        val currency = CurrencyEntity(code = "USD", rate = 1.0)
        val timestamp = System.currentTimeMillis()

        currencyDao.upsertCurrencyEntities(listOf(currency))
        currencyDao.updateCurrencyTimestamp(currency.toCurrencyTimestampEntity(timestamp))

        val fetchedCurrencies = currencyDao.getCurrencies().first()


        assertEquals(
            listOf(currency.toCurrencyWithTimestamp(timestamp)),
            fetchedCurrencies
        )
    }

    @Test
    fun upsertCurrencyEntities_returns_overridden_CurrencyWithTimestamp() = runTest {
        val currency = CurrencyEntity(code = "USD", rate = 1.0)
        val currencyOverridden = CurrencyEntity(code = "USD", rate = 2.0)
        val timestamp = System.currentTimeMillis()

        currencyDao.upsertCurrencyEntities(listOf(currency))
        currencyDao.upsertCurrencyEntities(listOf(currencyOverridden))
        currencyDao.updateCurrencyTimestamp(currency.toCurrencyTimestampEntity(timestamp))

        val fetchedCurrencies = currencyDao.getCurrencies().first()


        assertEquals(
            listOf(currencyOverridden.toCurrencyWithTimestamp(timestamp)),
            fetchedCurrencies
        )
    }

    @Test
    fun empty_database_returns_empty_CurrencyWithTimestamp_list() = runTest {
        val fetchedCurrencies = currencyDao.getCurrencies().first()

        assertEquals(
            listOf<CurrencyWithTimestamp>(),
            fetchedCurrencies
        )
    }



    private fun CurrencyEntity.toCurrencyWithTimestamp(timestamp: Long?) =
        CurrencyWithTimestamp(
            code = this.code,
            rate = this.rate,
            timestamp = timestamp
        )

    private fun CurrencyEntity.toCurrencyTimestampEntity(timestamp: Long) =
        CurrencyTimestampEntity(
            currencyCode = this.code,
            timestamp = timestamp
        )
}