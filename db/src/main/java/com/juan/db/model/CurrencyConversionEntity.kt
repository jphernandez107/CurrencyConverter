package com.juan.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_conversions")
data class CurrencyConversionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val fromCurrency: String,
    val toCurrency: String,
    val rate: Double,
    val amount: Double,
    val result: Double,
    val timestamp: String, // TODO: Ver si usamos un tipo de dato diferente
)
