package com.juan.ui.screen.history

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface HistoryViewState {
    @Immutable
    data object Loading : HistoryViewState

    @Immutable
    data object Empty : HistoryViewState

    @Immutable
    data class Success(
        val history: List<HistoryItem>,
    ) : HistoryViewState {
        @Immutable
        data class HistoryItem(
            val id: Long,
            val title: String,
            val rate: String,
            val summary: String,
            val timestamp: String,
        )
    }
}