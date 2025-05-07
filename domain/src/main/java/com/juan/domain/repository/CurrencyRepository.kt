package com.juan.domain.repository

import com.juan.domain.model.CurrencyConversion
import com.juan.domain.model.Result
import com.juan.domain.value.Currency
import com.juan.domain.value.Money
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun convertCurrency(
        from: Currency,
        to: Currency,
        amount: Money,
    ): Result<CurrencyConversion>

    fun getConversionHistory(): Flow<List<CurrencyConversion>>

    suspend fun getConversionDetails(id: Long): CurrencyConversion?
}