package com.juan.db.mapper

import com.juan.db.model.CurrencyConversionEntity
import com.juan.domain.model.ConversionResult
import com.juan.domain.model.CurrencyConversion
import com.juan.domain.value.Currency
import com.juan.domain.value.Money
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val formatter = DateTimeFormatter.ISO_DATE_TIME

fun CurrencyConversionEntity.toDomain() = CurrencyConversion(
    id = id,
    from = Currency.fromCode(fromCurrency),
    to = Currency.fromCode(toCurrency),
    rate = rate,
    amount = Money(amount),
    result = ConversionResult(
        amount = Money(result),
        currency = Currency.fromCode(toCurrency),
    ),
    timestamp = LocalDateTime.parse(timestamp, formatter),
)

fun CurrencyConversion.toEntity() = CurrencyConversionEntity(
    id = id,
    fromCurrency = from.code,
    toCurrency = to.code,
    rate = rate,
    amount = amount.value,
    result = result.amount.value,
    timestamp = timestamp.format(formatter),
)