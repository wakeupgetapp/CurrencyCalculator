package com.wakeupgetapp.core.database.internal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.wakeupgetapp.core.database.internal.model.CurrencyEntity
import com.wakeupgetapp.core.database.internal.model.CurrencyTimestampEntity
import com.wakeupgetapp.core.database.internal.model.CurrencyWithTimestamp
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Upsert
    suspend fun upsertCurrencyEntities(currencyEntities: List<CurrencyEntity>)

    @Transaction
    @Query("SELECT currencyList.code, currencyList.rate, currencyTimestampList.timestamp " +
            "FROM currencyList LEFT JOIN currencyTimestampList " +
            "ON currencyList.code = currencyTimestampList.currencyCode")
    fun getCurrencies(): Flow<List<CurrencyWithTimestamp>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCurrencyTimestamp(currency: CurrencyTimestampEntity)
}