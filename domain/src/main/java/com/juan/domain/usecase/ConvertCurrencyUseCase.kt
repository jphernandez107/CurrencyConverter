package com.juan.domain.usecase

import com.juan.domain.repository.CurrencyRepository
import com.juan.domain.value.Currency
import com.juan.domain.value.Money

/**
 * Caso de uso para convertir un monto entre monedas utilizando el repositorio.
 *
 * @property repository Repositorio que realiza la operación de conversión.
 */
class ConvertCurrencyUseCase(
    private val repository: CurrencyRepository,
) {
    /**
     * Ejecuta la conversión de moneda.
     *
     * @param fromCurrency Moneda de origen.
     * @param toCurrency Moneda de destino.
     * @param amount Monto a convertir.
     * @return Un [Result] con la [CurrencyConversion] resultante.
     */
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