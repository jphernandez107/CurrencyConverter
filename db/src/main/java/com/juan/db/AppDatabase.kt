package com.juan.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.juan.db.dao.CurrencyConversionDao
import com.juan.db.model.CurrencyConversionEntity

@Database(
    entities = [
        CurrencyConversionEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyConversionDao(): CurrencyConversionDao
}