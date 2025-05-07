package com.juan.ui.screen.convert

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.juan.domain.value.Currency
import com.juan.ui.model.CurrencyState
import com.juan.ui.model.toCurrencyState
import com.juan.ui.navigation.Screen

@Composable
fun ConvertScreen(
    viewModel: ConvertViewModel = hiltViewModel(),
    navController: NavController,
) {
    val viewState by viewModel.viewState.collectAsState()
    Convert(
        viewState = viewState,
        onEvent = { viewModel.onEvent(it) },
        onHistoryButtonClick = {
            navController.navigate(Screen.History.route)
        },
    )
}

@Composable
private fun Convert(
    viewState: ConvertViewState,
    onEvent: (ConvertUIEvent) -> Unit,
    onHistoryButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = viewState.amount,
            onValueChange = { onEvent(ConvertUIEvent.OnAmountChange(it)) },
            label = { Text("Cantidad") },
            enabled = viewState !is ConvertViewState.Loading,
            supportingText = {
                if (viewState is ConvertViewState.Error) {
                    Text(
                        text = viewState.error,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            },
            isError = viewState is ConvertViewState.Error,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            )
        )
        CurrencyDropdown(
            label = "Convertir de",
            selectedItem = viewState.fromCurrency,
            enabled = viewState !is ConvertViewState.Loading,
            onItemSelected = { onEvent(ConvertUIEvent.OnFromCurrencyChange(it)) },
        )
        CurrencyDropdown(
            label = "Convertir a",
            selectedItem = viewState.toCurrency,
            enabled = viewState !is ConvertViewState.Loading,
            onItemSelected = { onEvent(ConvertUIEvent.OnToCurrencyChange(it)) },
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            if (viewState is ConvertViewState.ConversionResult) {
                when (viewState) {
                    is ConvertViewState.ConversionResult.Success -> {
                        Text(
                            text = "Resultado: ${viewState.result.formattedResult}",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                    is ConvertViewState.ConversionResult.Failure -> {
                        Text(
                            text = "Error: ${viewState.error}",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
            ConvertButton(
                viewState = viewState,
                onClick = { onEvent(ConvertUIEvent.OnConvertClick) },
            )
        }


        Button(
            enabled = viewState !is ConvertViewState.Loading,
            onClick = onHistoryButtonClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Historial de conversiones",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun ConvertButton(
    viewState: ConvertViewState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val enabled = viewState is ConvertViewState.Convert ||
            viewState is ConvertViewState.ConversionResult
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
    ) {
        when (viewState) {
            is ConvertViewState.Convert -> Text("Convertir")
            is ConvertViewState.ConversionResult -> Text("Convertir")
            is ConvertViewState.Loading -> Text("Cargando...")
            is ConvertViewState.Error -> Text("Convertir")
        }
    }
}

@Composable
private fun CurrencyDropdown(
    label: String,
    selectedItem: CurrencyState,
    enabled: Boolean,
    onItemSelected: (CurrencyState) -> Unit,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Box {
        OutlinedTextField(
            value = "${selectedItem.code} (${selectedItem.symbol})",
            onValueChange = {},
            enabled = enabled,
            readOnly = true,
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled) {
                    expanded = true
                },
            trailingIcon = {
                IconButton(
                    onClick = { expanded = true },
                    enabled = enabled,
                ) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            Currency.entries.forEach { currency ->
                DropdownMenuItem(
                    text = {
                        Text("${currency.code} (${currency.symbol})")
                    },
                    onClick = {
                        expanded = false
                        onItemSelected(currency.toCurrencyState())
                    }
                )
            }
        }
    }
}


@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
internal fun ConvertScreenPreview() {
    MaterialTheme {
        Convert(
            viewState = ConvertViewState.Convert(
                fromCurrency = Currency.USD.toCurrencyState(),
                toCurrency = Currency.EUR.toCurrencyState(),
                amount = 100.0.toString(),
            ),
            onEvent = {  },
            onHistoryButtonClick = { },
        )
    }
}