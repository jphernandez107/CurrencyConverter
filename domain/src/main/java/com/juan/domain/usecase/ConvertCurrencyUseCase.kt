package com.juan.domain.usecase

import com.juan.domain.repository.CurrencyRepository
import com.juan.domain.value.Currency
import com.juan.domain.value.Money

class ConvertCurrencyUseCase(
    private val repository: CurrencyRepository,
) {
    suspend operator fun invoke(
        fromCurrency: Currency,
        toCurrency: Currency,
        amount: Money,
    ) = repository.convertCurrency(
        from = fromCurrency,
        to = toCurrency,
        amount = amount,
    )
}