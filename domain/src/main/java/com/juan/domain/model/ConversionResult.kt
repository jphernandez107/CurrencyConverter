package com.juan.domain.model

import com.juan.domain.value.Currency
import com.juan.domain.value.Money

data class ConversionResult(
    val amount: Money,
    val currency: Currency
)