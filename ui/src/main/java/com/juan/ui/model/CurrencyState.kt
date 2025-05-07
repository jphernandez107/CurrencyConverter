package com.juan.ui.model

import com.juan.domain.value.Currency

data class CurrencyState(
    val code: String,
    val symbol: String
)

fun CurrencyState.toCurrencyDomain() = Currency.fromCode(code)

fun Currency.toCurrencyState() = CurrencyState(
    code = code,
    symbol = symbol
)