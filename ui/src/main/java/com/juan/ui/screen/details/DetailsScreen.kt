package com.juan.ui.screen.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.juan.ui.model.CurrencyState

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel(),
) {
    val viewState by viewModel.viewState.collectAsState()
    when (val state = viewState) {
        is DetailsViewState.Loading -> Loading()
        is DetailsViewState.Success -> {
            DetailsContent(conversion = state.conversion)
        }
        is DetailsViewState.Error -> {
            Error(message = state.message)
        }
    }
}

@Composable
private fun DetailsContent(
    conversion: DetailsViewState.Success.ConversionDetailsState,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextRow(
                    label = "De:",
                    value = conversion.fromCurrency.code
                )
                TextRow(
                    label = "A:",
                    value = conversion.toCurrency.code
                )
                TextRow(
                    label = "Cantidad:",
                    value = conversion.amount
                )
                TextRow(
                    label = "Resultado:",
                    value = conversion.result
                )
                TextRow(
                    label = "Tasa:",
                    value = conversion.rate
                )
                TextRow(
                    label = "Fecha:",
                    value = conversion.timestamp
                )
            }
        }
    }
}

@Composable
private fun TextRow(
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = value
        )
    }
}

@Composable
private fun Loading() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun Error(message: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message)
    }
}

@Preview
@Composable
private fun DetailsScreenPreview() = MaterialTheme {
    DetailsContent(
        conversion = DetailsViewState.Success.ConversionDetailsState(
            fromCurrency = CurrencyState("USD", "$"),
            toCurrency = CurrencyState("EUR", "€"),
            amount = "100.00",
            result = "85.00",
            rate = "0.85",
            timestamp = "23-10-2023 12:00:00",
        ),
    )
}