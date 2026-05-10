package com.example.proyecto_final_seminario2.ui.explorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_final_seminario2.data.explorer.models.Business
import com.example.proyecto_final_seminario2.data.explorer.models.BusinessCategory
import com.example.proyecto_final_seminario2.data.explorer.repositories.ExplorerRepository
import com.example.proyecto_final_seminario2.data.explorer.repositories.MockExplorerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ExplorerUiState(
    val businesses: List<Business> = emptyList(),
    val filtered: List<Business> = emptyList(),
    val selectedCategory: BusinessCategory = BusinessCategory.ALL,
    val query: String = ""
)

class ExplorerViewModel(
    private val repository: ExplorerRepository = MockExplorerRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow(ExplorerUiState())
    val uiState: StateFlow<ExplorerUiState> = _uiState

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            val items = repository.getBusinesses()
            _uiState.value = _uiState.value.copy(businesses = items, filtered = items)
        }
    }

    fun selectCategory(category: BusinessCategory) {
        val current = _uiState.value
        _uiState.value = current.copy(selectedCategory = category)
        applyFilters(current.query, category, current.businesses)
    }

    fun setQuery(query: String) {
        val current = _uiState.value
        _uiState.value = current.copy(query = query)
        applyFilters(query, current.selectedCategory, current.businesses)
    }

    private fun applyFilters(query: String, category: BusinessCategory, source: List<Business>) {
        var result = source
        if (category != BusinessCategory.ALL) {
            result = result.filter { it.category == category }
        }
        if (query.isNotBlank()) {
            val q = query.lowercase()
            result = result.filter { it.name.lowercase().contains(q) || it.tags.any { t -> t.lowercase().contains(q) } }
        }
        _uiState.value = _uiState.value.copy(filtered = result)
    }
}

