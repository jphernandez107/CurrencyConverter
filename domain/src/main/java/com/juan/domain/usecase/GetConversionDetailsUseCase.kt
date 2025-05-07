package com.juan.domain.usecase

import com.juan.domain.repository.CurrencyRepository

class GetConversionDetailsUseCase(
    private val repository: CurrencyRepository,
) {
    suspend operator fun invoke(
        id: Long,
    ) = repository.getConversionDetails(
        id = id,
    )
}