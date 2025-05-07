package com.juan.ui.screen.convert

import com.juan.ui.model.CurrencyState

sealed interface ConvertUIEvent {
    data class OnFromCurrencyChange(val currency: CurrencyState) : ConvertUIEvent
    data class OnToCurrencyChange(val currency: CurrencyState) : ConvertUIEvent
    data class OnAmountChange(val amount: String) : ConvertUIEvent
    data object OnConvertClick : ConvertUIEvent
    data object OnClear : ConvertUIEvent
}