package com.juan.ui.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juan.domain.model.CurrencyConversion
import com.juan.domain.usecase.GetConversionHistoryUseCase
import com.juan.domain.utils.formatToString
import com.juan.domain.utils.moneyFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel  @Inject constructor(
    private val getConversionHistoryUseCase: GetConversionHistoryUseCase,
) : ViewModel() {
    private val _viewState = MutableStateFlow<HistoryViewState>(HistoryViewState.Loading)
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            getConversionHistoryUseCase().collectLatest { list ->
                _viewState.value = if (list.isEmpty()) {
                    HistoryViewState.Empty
                } else {
                    HistoryViewState.Success(
                        history = list.map { currencyConversion ->
                            currencyConversion.toHistoryItem()
                        },
                    )
                }
            }
        }
    }
}

fun CurrencyConversion.toHistoryItem() = HistoryViewState.Success.HistoryItem(
    id = id,
    title = "$from a $to",
    summary = "${amount.formattedWithCurrency(from)} ➡ ${result.amount.formattedWithCurrency(result.currency)}",
    rate = rate.moneyFormat(),
    timestamp = timestamp.formatToString(),
)