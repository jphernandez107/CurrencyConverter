package com.juan.domain.repository

import com.juan.domain.model.CurrencyConversion
import com.juan.domain.model.Result
import com.juan.domain.value.Currency
import com.juan.domain.value.Money
import kotlinx.coroutines.flow.Flow

/**
 * Interface del repositorio para operaciones relacionadas con la conversión de monedas.
 */
interface CurrencyRepository {
    /**
     * Convierte un monto dado de una moneda a otra utilizando tasas de cambio en tiempo real.
     *
     * @param from Moneda de origen.
     * @param to Moneda de destino.
     * @param amount Monto a convertir.
     * @return Un [Result] con la [CurrencyConversion] si fue exitosa, o un error.
     */
    suspend fun convertCurrency(
        from: Currency,
        to: Currency,
        amount: Money,
    ): Result<CurrencyConversion>

    /**
     * Devuelve un flow con el historial de conversiones realizadas.
     *
     * @return Un [Flow] que emite listas de [CurrencyConversion].
     */
    fun getConversionHistory(): Flow<List<CurrencyConversion>>

    /**
     * Obtiene los detalles de una conversión específica a partir de su id.
     *
     * @param id ID de la conversión.
     * @return La [CurrencyConversion] correspondiente, o null si no se encuentra.
     */
    suspend fun getConversionDetails(id: Long): CurrencyConversion?
}