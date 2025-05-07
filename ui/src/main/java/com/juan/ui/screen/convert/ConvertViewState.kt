package com.juan.ui.screen.convert

import androidx.compose.runtime.Immutable
import com.juan.domain.value.Currency
import com.juan.ui.model.CurrencyState
import com.juan.ui.model.toCurrencyState

@Immutable
sealed class ConvertViewState(
    open val fromCurrency: CurrencyState,
    open val toCurrency: CurrencyState,
    open val amount: String,
) {
    @Immutable
    data class Convert(
        override val fromCurrency: CurrencyState,
        override val toCurrency: CurrencyState,
        override val amount: String,
    ) : ConvertViewState(
        fromCurrency = fromCurrency,
        toCurrency = toCurrency,
        amount = amount,
    )

    @Immutable
    sealed class ConversionResult(
        override val fromCurrency: CurrencyState,
        override val toCurrency: CurrencyState,
        override val amount: String,
    ) : ConvertViewState(
        fromCurrency = fromCurrency,
        toCurrency = toCurrency,
        amount = amount,
    ) {
        @Immutable
        data class Success(
            override val fromCurrency: CurrencyState,
            override val toCurrency: CurrencyState,
            override val amount: String,
            val result: ConversionResultState,
        ) : ConversionResult(
            fromCurrency = fromCurrency,
            toCurrency = toCurrency,
            amount = amount,
        )

        @Immutable
        data class Failure(
            override val fromCurrency: CurrencyState,
            override val toCurrency: CurrencyState,
            override val amount: String,
            val error: String,
        ) : ConversionResult(
            fromCurrency = fromCurrency,
            toCurrency = toCurrency,
            amount = amount,
        )
    }

    @Immutable
    data class Loading(
        override val fromCurrency: CurrencyState,
        override val toCurrency: CurrencyState,
        override val amount: String,
    ) : ConvertViewState(
        fromCurrency = fromCurrency,
        toCurrency = toCurrency,
        amount = amount,
    )

    @Immutable
    data class Error(
        override val fromCurrency: CurrencyState,
        override val toCurrency: CurrencyState,
        override val amount: String,
        val error: String,
    ) : ConvertViewState(
        fromCurrency = fromCurrency,
        toCurrency = toCurrency,
        amount = amount,
    )

    companion object {
        val Default = Convert(
            fromCurrency = Currency.USD.toCurrencyState(),
            toCurrency = Currency.ARS.toCurrencyState(),
            amount = 10.0.toString(),
        )
    }
}
