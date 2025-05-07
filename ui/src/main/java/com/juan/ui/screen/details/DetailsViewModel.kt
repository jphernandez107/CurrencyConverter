package com.juan.ui.screen.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juan.domain.usecase.GetConversionDetailsUseCase
import com.juan.domain.utils.formatToString
import com.juan.domain.utils.moneyFormat
import com.juan.ui.model.toCurrencyState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getConversionDetailsUseCase: GetConversionDetailsUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _viewState = MutableStateFlow<DetailsViewState>(DetailsViewState.Loading)
    val viewState = _viewState.asStateFlow()

    init {
        val conversionId = savedStateHandle.get<String>(CONVERSION_ID_KEY)?.toLongOrNull()
        if (conversionId == null) {
            _viewState.value = DetailsViewState.Error("No se ha encontrado la conversión")
        } else {
            viewModelScope.launch {
                try {
                    val conversion = getConversionDetailsUseCase(conversionId)
                    if (conversion == null) {
                        _viewState.value = DetailsViewState.Error("No se ha encontrado la conversión")
                        return@launch
                    }
                    _viewState.value = DetailsViewState.Success(
                        conversion = DetailsViewState.Success.ConversionDetailsState(
                            fromCurrency = conversion.from.toCurrencyState(),
                            toCurrency = conversion.to.toCurrencyState(),
                            amount = conversion.amount.formatted(),
                            result = conversion.result.amount.formatted(),
                            rate = conversion.rate.moneyFormat(),
                            timestamp = conversion.timestamp.formatToString(),
                        ),
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                    _viewState.value = DetailsViewState.Error(
                        message = e.message ?: "Error desconocido",
                    )
                }
            }
        }
    }

    companion object {
        const val CONVERSION_ID_KEY = "id"
    }
}