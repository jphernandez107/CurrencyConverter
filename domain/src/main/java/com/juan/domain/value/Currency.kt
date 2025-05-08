package com.juan.domain.value

enum class Currency(val code: String, val symbol: String) {
    ARS("ARS", "AR$"),
    USD("USD", "$"),
    EUR("EUR", "€"),
    GBP("GBP", "£"),
    JPY("JPY", "¥"),
    AUD("AUD", "A$"),
    CAD("CAD", "C$"),
    CHF("CHF", "CHF"),
    CNY("CNY", "¥"),
    INR("INR", "₹"),
    PPP("Error", "For testing"),
    ;

    companion object {
        fun fromCode(code: String): Currency = entries.find { it.code == code } ?: USD
    }
}