package com.juan.domain.usecase

import com.juan.domain.repository.CurrencyRepository

/**
 * Caso de uso para obtener los detalles de una conversión a partir de su ID.
 *
 * @property repository Repositorio utilizado para acceder a los datos de conversión.
 */
class GetConversionDetailsUseCase(
    private val repository: CurrencyRepository,
) {
    /**
     * Ejecuta la consulta para obtener los datos de una conversión específica.
     *
     * @param id Identificador de la conversión.
     * @return La [CurrencyConversion] correspondiente o null si no existe.
     */
    suspend operator fun invoke(
        id: Long,
    ) = repository.getConversionDetails(
        id = id,
    )
}