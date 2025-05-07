package com.juan.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.juan.db.model.CurrencyConversionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyConversionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversion(conversion: CurrencyConversionEntity)

    @Query("SELECT * FROM currency_conversions ORDER BY timestamp DESC")
    fun getAllConversions(): Flow<List<CurrencyConversionEntity>>

    @Query("SELECT * FROM currency_conversions WHERE id = :id")
    suspend fun getConversionById(id: Long): CurrencyConversionEntity?
}