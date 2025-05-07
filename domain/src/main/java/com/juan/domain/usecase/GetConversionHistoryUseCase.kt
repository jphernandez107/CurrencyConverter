package com.juan.domain.usecase

import com.juan.domain.repository.CurrencyRepository

class GetConversionHistoryUseCase(
    private val repository: CurrencyRepository,
) {
    suspend operator fun invoke() = repository.getConversionHistory()
}