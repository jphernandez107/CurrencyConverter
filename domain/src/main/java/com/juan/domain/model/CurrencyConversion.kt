package com.juan.domain.model

import com.juan.domain.value.Currency
import com.juan.domain.value.Money
import java.time.LocalDateTime

data class CurrencyConversion(
    val id: Long = 0,
    val from: Currency,
    val to: Currency,
    val rate: Double,
    val amount: Money,
    val result: ConversionResult,
    val timestamp: LocalDateTime,
)