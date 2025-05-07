package com.juan.domain.value

import com.juan.domain.utils.moneyFormat
import java.text.NumberFormat

@JvmInline
value class Money(val value: Double) {
    fun formattedWithCurrency(currency: Currency): String {
        val formatter = NumberFormat.getNumberInstance()
        formatter.maximumFractionDigits = 2
        formatter.minimumFractionDigits = 2
        return "${currency.symbol} ${value.moneyFormat()}"
    }
    fun formatted() = value.moneyFormat()
}