package com.juan.ui.screen.convert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juan.domain.model.CurrencyConversion
import com.juan.domain.model.Result
import com.juan.domain.usecase.ConvertCurrencyUseCase
import com.juan.domain.usecase.GetConversionHistoryUseCase
import com.juan.domain.value.Money
import com.juan.ui.model.CurrencyState
import com.juan.ui.model.toCurrencyDomain
import com.juan.ui.screen.history.HistoryViewState
import com.juan.ui.screen.history.toHistoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConvertViewModel @Inject constructor(
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val getConversionHistoryUseCase: GetConversionHistoryUseCase,
) : ViewModel() {
    private val _viewState = MutableStateFlow<ConvertViewState>(ConvertViewState.Default)
    val viewState = _viewState.asStateFlow()

    private val _recentConversionsViewState = MutableStateFlow<HistoryViewState>(HistoryViewState.Loading)
    val recentConversionsViewState = _recentConversionsViewState.asStateFlow()

    init {
        viewModelScope.launch {
            getConversionHistoryUseCase().collectLatest { list ->
                _recentConversionsViewState.value = if (list.isEmpty()) {
                    HistoryViewState.Empty
                } else {
                    HistoryViewState.Success(
                        history = list.take(2).map { currencyConversion ->
                            currencyConversion.toHistoryItem()
                        },
                    )
                }
            }
        }
    }

    private fun onAmountChange(value: String) {
        verifyAmount(value)?.let {
            when (val state = viewState.value) {
                is ConvertViewState.Convert -> _viewState.value = state.copy(amount = value)
                is ConvertViewState.Error -> _viewState.value = ConvertViewState.Convert(
                    fromCurrency = state.fromCurrency,
                    toCurrency = state.toCurrency,
                    amount = value,
                )
                is ConvertViewState.Loading -> Unit
                is ConvertViewState.ConversionResult.Success -> _viewState.value = state.copy(amount = value)
                is ConvertViewState.ConversionResult.Failure -> _viewState.value = state.copy(amount = value)
            }
        }
    }

    private fun onFromCurrencyChange(value: CurrencyState) {
        when (val state = viewState.value) {
            is ConvertViewState.Convert -> _viewState.value = state.copy(fromCurrency = value)
            is ConvertViewState.Error -> _viewState.value = state.copy(fromCurrency = value)
            is ConvertViewState.Loading -> Unit
            is ConvertViewState.ConversionResult.Success -> _viewState.value = state.copy(fromCurrency = value)
            is ConvertViewState.ConversionResult.Failure -> _viewState.value = state.copy(fromCurrency = value)
        }
    }

    private fun onToCurrencyChange(value: CurrencyState) {
        when (val state = viewState.value) {
            is ConvertViewState.Convert -> _viewState.value = state.copy(toCurrency = value)
            is ConvertViewState.Error -> _viewState.value = state.copy(toCurrency = value)
            is ConvertViewState.Loading -> Unit
            is ConvertViewState.ConversionResult.Success -> _viewState.value = state.copy(toCurrency = value)
            is ConvertViewState.ConversionResult.Failure -> _viewState.value = state.copy(toCurrency = value)

        }
    }

    fun onEvent(event: ConvertUIEvent) {
        when (event) {
            is ConvertUIEvent.OnFromCurrencyChange -> onFromCurrencyChange(event.currency)
            is ConvertUIEvent.OnToCurrencyChange -> onToCurrencyChange(event.currency)
            is ConvertUIEvent.OnConvertClick -> convert()
            is ConvertUIEvent.OnAmountChange -> onAmountChange(event.amount)
            is ConvertUIEvent.OnClear -> Unit
        }
    }

    private fun convert() {
        val amountString = viewState.value.amount
        verifyAmount(amountString)?.let { amount ->
            viewModelScope.launch {
                val fromCurrency = viewState.value.fromCurrency
                val toCurrency = viewState.value.toCurrency
                _viewState.value = ConvertViewState.Loading(
                    fromCurrency = fromCurrency,
                    toCurrency = toCurrency,
                    amount = amount.toString(),
                )
                val result = convertCurrencyUseCase(
                    fromCurrency = fromCurrency.toCurrencyDomain(),
                    toCurrency = toCurrency.toCurrencyDomain(),
                    amount = Money(amount),
                )
                when (result) {
                    is Result.Success -> {
                        _viewState.value = ConvertViewState.ConversionResult.Success(
                            fromCurrency = fromCurrency,
                            toCurrency = toCurrency,
                            amount = amountString,
                            result = result.data.toConversionResultState(),
                        )
                    }
                    is Result.Error -> {
                        _viewState.value = ConvertViewState.ConversionResult.Failure(
                            fromCurrency = fromCurrency,
                            toCurrency = toCurrency,
                            amount = amountString,
                            error = result.message,
                        )
                    }
                }
            }
        }
    }

    private fun verifyAmount(amount: String): Double? {
        val amountDouble = amount.toDoubleOrNull()
        val errorMessage = when {
            amountDouble == null -> "El monto debe ser un número"
            amountDouble <= 0 -> "El monto debe ser mayor a 0"
            else -> null
        }
        errorMessage?.let {
            _viewState.value = ConvertViewState.Error(
                fromCurrency = viewState.value.fromCurrency,
                toCurrency = viewState.value.toCurrency,
                amount = amount,
                error = it,
            )
            return null
        }
        return amountDouble
    }

    private fun CurrencyConversion.toConversionResultState() = ConversionResultState(
        formattedResult = result.amount.formattedWithCurrency(result.currency),
    )
}