package com.juan.data.api

import com.juan.data.BuildConfig
import com.juan.data.dto.ConvertCurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {
    @GET("convert")
    suspend fun convertCurrency(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double,
        @Query("access_key") accessKey: String = BuildConfig.API_KEY,
    ): ConvertCurrencyResponse
}