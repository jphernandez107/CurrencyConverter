package com.juan.ui.screen.details

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.juan.ui.model.CurrencyState

@Stable
interface DetailsViewState {
    @Immutable
    data object Loading : DetailsViewState

    @Immutable
    data class Success(
        val conversion: ConversionDetailsState,
    ) : DetailsViewState {
        @Immutable
        data class ConversionDetailsState(
            val fromCurrency: CurrencyState,
            val toCurrency: CurrencyState,
            val amount: String,
            val result: String,
            val rate: String,
            val timestamp: String,
        )
    }

    @Immutable
    data class Error(
        val message: String,
    ) : DetailsViewState
}