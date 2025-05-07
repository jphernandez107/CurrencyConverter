package com.juan.data.mapper

import com.juan.data.dto.ConvertCurrencyResponse
import com.juan.domain.model.ConversionResult
import com.juan.domain.model.CurrencyConversion
import com.juan.domain.value.Currency
import com.juan.domain.value.Money
import java.time.LocalDateTime

fun ConvertCurrencyResponse.toDomain(
    from: Currency,
    to: Currency,
    amount: Money,
) : CurrencyConversion {
    val safeResult = result ?: throw IllegalStateException("Missing result value")
    val safeInfo = info ?: throw IllegalStateException("Missing info")
    val safeRate = safeInfo.rate
    val safeTimestamp = safeInfo.timestamp

    return CurrencyConversion(
        from = from,
        to = to,
        amount = amount,
        result = ConversionResult(
            amount = Money(safeResult),
            currency = to
        ),
        rate = safeRate,
        timestamp = safeTimestamp.toLocalDateTime()
    )
}

private fun String.toLocalDateTime(): LocalDateTime {
    return try {
        LocalDateTime.parse(this)
    } catch (e: Exception) {
        e.printStackTrace()
        LocalDateTime.now()
    }
}