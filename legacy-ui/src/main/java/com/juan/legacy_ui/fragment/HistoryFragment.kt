package com.juan.legacy_ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juan.legacy_ui.adapter.HistoryAdapter
import com.juan.ui.screen.history.HistoryViewModel
import com.juan.ui.screen.history.HistoryViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private val  viewModel: HistoryViewModel by viewModels()
    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val recyclerView = RecyclerView(requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = HistoryAdapter { id ->
            val action = HistoryFragmentDirections.actionHistoryToDetail(id.toString())
            findNavController().navigate(action)
        }
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.viewState.collectLatest { state ->
                if (state is HistoryViewState.Success) {
                    adapter.submitList(state.history)
                }
            }
        }
        return recyclerView
    }

}