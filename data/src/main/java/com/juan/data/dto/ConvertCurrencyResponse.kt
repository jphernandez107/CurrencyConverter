package com.juan.data.dto

import com.google.gson.annotations.SerializedName

data class ConvertCurrencyResponse(
    @SerializedName("result")
    val result: Double?,
    @SerializedName("info")
    val info: Info?,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("error")
    val error: ApiError?,
) {
    data class Info(
        @SerializedName("quote")
        val rate: Double,
        @SerializedName("timestamp")
        val timestamp: String,
    )
    data class ApiError(
        @SerializedName("code")
        val code: Int,
        @SerializedName("type")
        val info: String,
    )
}
