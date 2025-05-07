package com.juan.domain.utils

import java.text.NumberFormat

fun Double.moneyFormat(): String {
    val formatter = NumberFormat.getNumberInstance()
    formatter.maximumFractionDigits = 2
    formatter.minimumFractionDigits = 2
    return formatter.format(this)
}