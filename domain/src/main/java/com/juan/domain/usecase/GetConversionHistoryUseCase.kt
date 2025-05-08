package com.juan.domain.usecase

import com.juan.domain.repository.CurrencyRepository

/**
 * Caso de uso para obtener el historial de conversiones realizadas.
 *
 * @property repository Repositorio que proporciona el historial desde la base de datos local.
 */
class GetConversionHistoryUseCase(
    private val repository: CurrencyRepository,
) {
    /**
     * Ejecuta la operación para recuperar el historial de conversiones.
     *
     * @return Un [Flow] que emite listas de [CurrencyConversion] representando el historial.
     */
    operator fun invoke() = repository.getConversionHistory()
}