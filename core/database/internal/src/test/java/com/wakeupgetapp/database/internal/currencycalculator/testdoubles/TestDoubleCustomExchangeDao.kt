package com.wakeupgetapp.database.internal.currencycalculator.testdoubles

import com.wakeupgetapp.core.database.internal.dao.CustomExchangeDao
import com.wakeupgetapp.core.database.internal.model.CustomExchangeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class TestDoubleCustomExchangeDao : CustomExchangeDao {

    private val entities = MutableStateFlow(emptyList<CustomExchangeEntity>())
    private var currentId = 0L

    override fun getAllCustomExchanges(): Flow<List<CustomExchangeEntity>> = entities

    override suspend fun upsertCustomExchange(customExchange: CustomExchangeEntity) {
        entities.update { list ->
            val updatedList = list.toMutableList()
            if (customExchange.id == 0L) {
                currentId += 1
                updatedList.add(customExchange.copy(id = currentId))
            } else {
                updatedList.remove(updatedList.firstOrNull { it.id == customExchange.id })
                updatedList.add(customExchange)
            }
            updatedList
        }
    }

    override suspend fun deleteCustomExchangeById(id: Long) {
        entities.update { list ->
            val updatedList = list.toMutableList()
            updatedList.remove(updatedList.firstOrNull { it.id == id })
            updatedList
        }
    }
}