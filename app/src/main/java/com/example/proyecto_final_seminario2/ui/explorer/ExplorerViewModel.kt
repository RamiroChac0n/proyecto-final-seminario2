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

/**
 * Controla la pantalla de exploracion de negocios.
 *
 * Mantiene filtros, texto de busqueda, ubicacion actual y estado de carga.
 * Las busquedas pueden ser cercanas o por texto, ambas con respaldo de cache.
 */
class ExplorerViewModel(
    private val repository: ExplorerRepository = MockExplorerRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow(ExplorerUiState())
    val uiState: StateFlow<ExplorerUiState> = _uiState

    init {
        load()
    }

<<<<<<< Updated upstream
    private fun load() {
        viewModelScope.launch {
            val items = repository.getBusinesses()
            _uiState.value = _uiState.value.copy(businesses = items, filtered = items)
=======
    fun loadNearbyPlaces() {
        // Solo debe vivir una busqueda activa para evitar resultados fuera de orden.
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
                searchMode = PlaceSearchMode.NEARBY
            )

            val locationResult = locationRepository.getCurrentLocation()

            locationResult.onSuccess { location ->
                // Se conserva la ubicacion para sesgar futuras busquedas por texto.
                currentLocation = location

                val current = _uiState.value
                val category = PlaceCategory.fromBusinessCategory(current.selectedCategory)

                val result = placesRepository.searchNearby(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    radiusMeters = current.selectedRadius.meters,
                    category = category
                )

                result.onSuccess { searchResult ->
                    _uiState.value = _uiState.value.copy(
                        businesses = searchResult.places,
                        filtered = searchResult.places,
                        isLoading = false,
                        errorMessage = null,
                        isUsingCachedData = searchResult.isFromCache,
                        searchMode = PlaceSearchMode.NEARBY
                    )
                }.onFailure { throwable ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = throwable.message ?: "No se pudieron cargar lugares cercanos",
                        isUsingCachedData = false
                    )
                }
            }.onFailure { throwable ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = throwable.message ?: "No se pudo obtener tu ubicación"
                )
            }
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
    private fun applyFilters(query: String, category: BusinessCategory, source: List<Business>) {
        var result = source
        if (category != BusinessCategory.ALL) {
            result = result.filter { it.category == category }
=======
        if (query.isBlank()) {
            // Al limpiar el texto se vuelve al modo de exploracion cercana.
            loadNearbyPlaces()
>>>>>>> Stashed changes
        }
        if (query.isNotBlank()) {
            val q = query.lowercase()
            result = result.filter { it.name.lowercase().contains(q) || it.tags.any { t -> t.lowercase().contains(q) } }
        }
        _uiState.value = _uiState.value.copy(filtered = result)
    }
}

<<<<<<< Updated upstream
=======
    fun retry() {
        refreshSearch()
    }

    private fun refreshSearch() {
        val query = _uiState.value.query.trim()

        // Los cambios de filtro respetan el modo actual del usuario.
        if (query.isBlank()) {
            loadNearbyPlaces()
        } else {
            searchByText(query)
        }
    }

    private fun searchByText(query: String) {
        // Cancela busquedas previas si el usuario cambia filtros o envia otra consulta.
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
                searchMode = PlaceSearchMode.TEXT
            )

            val location = currentLocation ?: locationRepository
                .getCurrentLocation()
                .getOrNull()
                ?.also { currentLocation = it }

            val current = _uiState.value
            val category = PlaceCategory.fromBusinessCategory(current.selectedCategory)

            val result = placesRepository.searchByText(
                query = query,
                latitude = location?.latitude,
                longitude = location?.longitude,
                radiusMeters = current.selectedRadius.meters,
                category = category
            )

            result.onSuccess { searchResult ->
                _uiState.value = _uiState.value.copy(
                    businesses = searchResult.places,
                    filtered = searchResult.places,
                    isLoading = false,
                    errorMessage = null,
                    isUsingCachedData = searchResult.isFromCache,
                    searchMode = PlaceSearchMode.TEXT
                )
            }.onFailure { throwable ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = throwable.message ?: "No se pudo realizar la búsqueda",
                    isUsingCachedData = false
                )
            }
        }
    }
}
>>>>>>> Stashed changes
