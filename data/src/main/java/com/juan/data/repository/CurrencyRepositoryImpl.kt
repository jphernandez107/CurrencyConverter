package com.juan.data.repository

import com.juan.data.api.CurrencyApiService
import com.juan.data.mapper.toDomain
import com.juan.db.dao.CurrencyConversionDao
import com.juan.db.mapper.toDomain
import com.juan.db.mapper.toEntity
import com.juan.domain.model.CurrencyConversion
import com.juan.domain.model.Result
import com.juan.domain.repository.CurrencyRepository
import com.juan.domain.value.Currency
import com.juan.domain.value.Money
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CurrencyRepositoryImpl(
    private val api: CurrencyApiService,
    private val dao: CurrencyConversionDao,
) : CurrencyRepository {

    override suspend fun convertCurrency(
        from: Currency,
        to: Currency,
        amount: Money
    ): Result<CurrencyConversion> {
        return try {
            val response = api.convertCurrency(from.code, to.code, amount.value)
            return if (response.success) {
                val domainModel = response.toDomain(from, to, amount)
                dao.insertConversion(domainModel.toEntity())
                Result.Success(domainModel)
            } else {
                Result.Error(response.error?.info ?: "Error desconocido")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.localizedMessage ?: "Error desconocido")
        }
    }

    override fun getConversionHistory(): Flow<List<CurrencyConversion>> =
        dao.getAllConversions().map { list ->
            list.map { it.toDomain() }
        }

    override suspend fun getConversionDetails(id: Long) =
        dao.getConversionById(id)?.toDomain()

}