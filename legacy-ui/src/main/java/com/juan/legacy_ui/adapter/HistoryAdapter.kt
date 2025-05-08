package com.juan.legacy_ui.adapter

import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.juan.ui.screen.history.HistoryItem
import com.juan.ui.screen.history.HistoryViewState

class HistoryAdapter(
    private val onClick: (Long) -> Unit,
) : ListAdapter<HistoryViewState.Success.HistoryItem, HistoryAdapter.HistoryViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(ComposeView(parent.context), onClick)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HistoryViewHolder(
        private val composeView: ComposeView,
        private val onClick: (Long) -> Unit
    ) : RecyclerView.ViewHolder(composeView) {
        fun bind(historyItem: HistoryViewState.Success.HistoryItem) {
            with(composeView) {
                setContent {
                    HistoryItem(
                        historyItem = historyItem,
                        onItemClick = onClick,
                    )
                }
                val padding = (16 * resources.displayMetrics.density).toInt()
                setPadding(padding, padding / 2, padding, padding / 2)
            }
        }
    }

    companion object {
        private val DiffCallback =
            object : DiffUtil.ItemCallback<HistoryViewState.Success.HistoryItem>() {
                override fun areItemsTheSame(
                    oldItem: HistoryViewState.Success.HistoryItem,
                    newItem: HistoryViewState.Success.HistoryItem
                ) = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: HistoryViewState.Success.HistoryItem,
                    newItem: HistoryViewState.Success.HistoryItem
                ) = oldItem == newItem
            }
    }
}