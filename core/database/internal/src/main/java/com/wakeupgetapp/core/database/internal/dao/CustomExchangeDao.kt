package com.wakeupgetapp.core.database.internal.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.wakeupgetapp.core.database.internal.model.CustomExchangeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomExchangeDao {
    @Query("SELECT * FROM customExchangeEntity")
    fun getAllCustomExchanges(): Flow<List<CustomExchangeEntity>>

    @Upsert
    suspend fun upsertCustomExchange(customExchange: CustomExchangeEntity)

    @Query("DELETE FROM customExchangeEntity WHERE id = :id")
    suspend fun deleteCustomExchangeById(id: Long)
}