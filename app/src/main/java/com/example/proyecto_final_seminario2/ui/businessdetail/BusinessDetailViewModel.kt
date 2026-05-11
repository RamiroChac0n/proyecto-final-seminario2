package com.example.proyecto_final_seminario2.ui.businessdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_final_seminario2.data.places.models.PlaceDetailsItem
import com.example.proyecto_final_seminario2.data.places.repositories.PlaceDetailsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class BusinessDetailUiState(
    val place: PlaceDetailsItem? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isUsingCachedData: Boolean = false
)

class BusinessDetailViewModel(
    private val placeDetailsRepository: PlaceDetailsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BusinessDetailUiState())
    val uiState: StateFlow<BusinessDetailUiState> = _uiState

    private var currentPlaceId: String? = null

    fun loadPlaceDetails(placeId: String) {
        if (placeId.isBlank()) {
            _uiState.value = BusinessDetailUiState(
                errorMessage = "No se encontró el identificador del lugar."
            )
            return
        }

        currentPlaceId = placeId

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            val result = placeDetailsRepository.getPlaceDetails(placeId)

            result.onSuccess { detailsResult ->
                _uiState.value = BusinessDetailUiState(
                    place = detailsResult.place,
                    isLoading = false,
                    errorMessage = null,
                    isUsingCachedData = detailsResult.isFromCache
                )
            }.onFailure { throwable ->
                _uiState.value = BusinessDetailUiState(
                    isLoading = false,
                    errorMessage = throwable.message ?: "No se pudieron cargar los detalles del lugar."
                )
            }
        }
    }

    fun retry() {
        currentPlaceId?.let { placeId ->
            loadPlaceDetails(placeId)
        }
    }
}