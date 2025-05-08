package com.juan.ui.screen.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    onItemClick: (Long) -> Unit,
) {
    val viewState by viewModel.viewState.collectAsState()
    when (val state = viewState) {
        is HistoryViewState.Loading -> Loading()
        is HistoryViewState.Empty -> Empty()
        is HistoryViewState.Success -> {
            HistoryList(
                historyItems = state.history,
                onItemClick = onItemClick,
            )
        }
    }
}

@Composable
private fun HistoryList(
    historyItems: List<HistoryViewState.Success.HistoryItem>,
    onItemClick: (Long) -> Unit = {},
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(
            bottom = 16.dp,
        )
    ) {
        items(historyItems, key = { it.id }) { historyItem ->
            HistoryItem(
                historyItem = historyItem,
                onItemClick = onItemClick,
            )
        }
    }
}

@Composable
private fun Loading(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun Empty(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "No hay conversiones aún.",
        )
    }
}

@Preview
@Composable
private fun HistoryScreenPreview() = MaterialTheme {
    HistoryList(
        historyItems = listOf(
            HistoryViewState.Success.HistoryItem(
                id = 1,
                title = "USD a EUR",
                summary = "100 USD ➡ 85 EUR",
                rate = "0.85",
                timestamp = "23-10-2023 12:00:00",
            ),
            HistoryViewState.Success.HistoryItem(
                id = 2,
                title = "EUR a GBP",
                summary = "200 EUR ➡ 170 GBP",
                rate = "0.86",
                timestamp = "10-10-2023 12:00:00",
            ),
        ),
    )
}