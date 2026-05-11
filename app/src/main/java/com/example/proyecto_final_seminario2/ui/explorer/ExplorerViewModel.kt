package com.example.proyecto_final_seminario2.ui.explorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_final_seminario2.data.explorer.models.Business
import com.example.proyecto_final_seminario2.data.explorer.models.BusinessCategory
import com.example.proyecto_final_seminario2.data.explorer.repositories.ExplorerRepository
import com.example.proyecto_final_seminario2.data.explorer.repositories.MockExplorerRepository
import com.example.proyecto_final_seminario2.data.location.models.UserLocation
import com.example.proyecto_final_seminario2.data.location.repositories.LocationRepository
import com.example.proyecto_final_seminario2.data.places.models.PlaceCategory
import com.example.proyecto_final_seminario2.data.places.models.PlaceSearchMode
import com.example.proyecto_final_seminario2.data.places.models.SearchRadiusOption
import com.example.proyecto_final_seminario2.data.places.repositories.PlacesRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ExplorerUiState(
    val businesses: List<Business> = emptyList(),
    val filtered: List<Business> = emptyList(),
    val selectedCategory: BusinessCategory = BusinessCategory.ALL,
    val selectedRadius: SearchRadiusOption = SearchRadiusOption.THREE_KM,
    val query: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val hasLocationPermission: Boolean = false,
    val isUsingCachedData: Boolean = false,
    val searchMode: PlaceSearchMode = PlaceSearchMode.NEARBY
)

class ExplorerViewModel(
    private val placesRepository: PlacesRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExplorerUiState())
    val uiState: StateFlow<ExplorerUiState> = _uiState

    private var currentLocation: UserLocation? = null
    private var searchJob: Job? = null

    fun onLocationPermissionResult(granted: Boolean) {
        _uiState.value = _uiState.value.copy(
            hasLocationPermission = granted,
            errorMessage = if (granted) null else "Necesitamos tu ubicación para buscar lugares cercanos."
        )

        if (granted) {
            loadNearbyPlaces()
        }
    }

    fun loadNearbyPlaces() {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
                searchMode = PlaceSearchMode.NEARBY
            )

            val locationResult = locationRepository.getCurrentLocation()

            locationResult.onSuccess { location ->
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
        }
    }

    fun selectCategory(category: BusinessCategory) {
        _uiState.value = _uiState.value.copy(
            selectedCategory = category
        )

        refreshSearch()
    }

    fun selectRadius(radius: SearchRadiusOption) {
        _uiState.value = _uiState.value.copy(
            selectedRadius = radius
        )

        refreshSearch()
    }

    fun setQuery(query: String) {
        _uiState.value = _uiState.value.copy(
            query = query
        )

        if (query.isBlank()) {
            loadNearbyPlaces()
        }
    }

    fun submitSearch() {
        val query = _uiState.value.query.trim()

        if (query.isBlank()) {
            loadNearbyPlaces()
            return
        }

        searchByText(query)
    }

    fun retry() {
        refreshSearch()
    }

    private fun refreshSearch() {
        val query = _uiState.value.query.trim()

        if (query.isBlank()) {
            loadNearbyPlaces()
        } else {
            searchByText(query)
        }
    }

    private fun searchByText(query: String) {
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