package com.wakeupgetapp.core.database.internal

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wakeupgetapp.core.database.internal.dao.CurrencyDao
import com.wakeupgetapp.core.database.internal.dao.CustomExchangeDao
import com.wakeupgetapp.core.database.internal.model.CurrencyEntity
import com.wakeupgetapp.core.database.internal.model.CurrencyTimestampEntity
import com.wakeupgetapp.core.database.internal.model.CurrencyUpdateEntity
import com.wakeupgetapp.core.database.internal.model.CustomExchangeEntity

@Database(
    entities = [
        CurrencyEntity::class,
        CurrencyUpdateEntity::class,
        CurrencyTimestampEntity::class,
        CustomExchangeEntity::class],
    version = 1,
    exportSchema = true
)
internal abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    abstract fun customExchangeDao(): CustomExchangeDao

}