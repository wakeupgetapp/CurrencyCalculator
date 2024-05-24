package com.wakeupgetapp.database.internal.currencycalculator

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.wakeupgetapp.core.database.internal.CurrencyDatabase
import com.wakeupgetapp.core.database.internal.dao.CustomExchangeDao
import com.wakeupgetapp.core.database.internal.model.CustomExchangeEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class CustomExchangeDaoTest {

    private lateinit var customExchangeDao: CustomExchangeDao
    private lateinit var db: CurrencyDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            CurrencyDatabase::class.java,
        ).build()
        customExchangeDao = db.customExchangeDao()
    }

    @After
    fun closeDb() = db.close()

    @Test
    fun empty_database_returns_empty_CustomExchangeEntity_list() = runTest {
        val fetchedCurrencies = customExchangeDao.getAllCustomExchanges().first()

        assertEquals(
            emptyList<CustomExchangeDao>(),
            fetchedCurrencies
        )
    }

    @Test
    fun upsertCustomExchange_generates_correct_ids() = runTest {
        val customExchangeEntityList = listOf(
            CustomExchangeEntity(0, 0, "USD", "EUR", 1.1),
            CustomExchangeEntity(0, 0, "GBP", "PLN", 5.101122)
        )

        customExchangeEntityList.forEach { customExchangeDao.upsertCustomExchange(it) }

        val fetchedCustomExchanges = customExchangeDao.getAllCustomExchanges().first()

        assertEquals(listOf(1L, 2L), fetchedCustomExchanges.map { it.id })
    }

    @Test
    fun getCustomExchangeEntity_returns_correctList() = runTest {
        val customExchangeEntityList = listOf(
            CustomExchangeEntity(1, 0, "USD", "EUR", 1.1),
            CustomExchangeEntity(2, 0, "GBP", "PLN", 5.101122)
        )

        customExchangeEntityList.forEach { customExchangeDao.upsertCustomExchange(it) }

        val fetchedCustomExchanges = customExchangeDao.getAllCustomExchanges().first()

        assertEquals(customExchangeEntityList, fetchedCustomExchanges)
    }

    @Test
    fun upsertCustomExchange_returns_overridden_values() = runTest {
        val customExchangeEntityList = listOf(
            CustomExchangeEntity(0, 0, "USD", "EUR", 1.1),
            CustomExchangeEntity(0, 0, "GBP", "PLN", 5.101122)
        )

        val customExchangeEntityListOverridden = listOf(
            CustomExchangeEntity(1, 0, "EUR", "USD", 0.9),
            CustomExchangeEntity(2, 0, "PLN", "GBP", 0.2525)
        )

        customExchangeEntityList.forEach { customExchangeDao.upsertCustomExchange(it) }

        customExchangeEntityListOverridden.forEach { customExchangeDao.upsertCustomExchange(it) }

        val fetchedCustomExchanges = customExchangeDao.getAllCustomExchanges().first()

        assertEquals(customExchangeEntityListOverridden, fetchedCustomExchanges)
    }

    @Test
    fun deleteCustomExchangeById_returns_newList() = runTest {
        val customExchangeEntityList = listOf(
            CustomExchangeEntity(1, 0, "EUR", "USD", 0.9),
            CustomExchangeEntity(2, 0, "PLN", "GBP", 0.2525)
        )

        customExchangeEntityList.forEach { customExchangeDao.upsertCustomExchange(it) }

        val fetchedCustomExchanges = customExchangeDao.getAllCustomExchanges().first()
        assertEquals(customExchangeEntityList, fetchedCustomExchanges)

        customExchangeDao.deleteCustomExchangeById(1L)

        val fetchedCustomExchangesAfterDeletion = customExchangeDao.getAllCustomExchanges().first()

        assertEquals(customExchangeEntityList.subList(1, 2), fetchedCustomExchangesAfterDeletion)
    }

    @Test
    fun delete_and_add_same_custom_exchange_gets_new_id() = runTest {
        val customExchangeEntityList = listOf(
            CustomExchangeEntity(0, 0, "EUR", "USD", 0.9),
            CustomExchangeEntity(0, 0, "PLN", "GBP", 0.2525)
        )

        customExchangeEntityList.forEach { customExchangeDao.upsertCustomExchange(it) }

        customExchangeDao.deleteCustomExchangeById(1L)

        customExchangeDao.upsertCustomExchange(CustomExchangeEntity(0, 0, "EUR", "USD", 0.9))

        val fetchedCustomExchangesAfterDeleteAndAdd = customExchangeDao.getAllCustomExchanges().first()

        val expectedList = listOf(
            CustomExchangeEntity(2, 0, "PLN", "GBP", 0.2525),
            CustomExchangeEntity(3, 0, "EUR", "USD", 0.9)
        )

        assertEquals(expectedList, fetchedCustomExchangesAfterDeleteAndAdd)
    }
}