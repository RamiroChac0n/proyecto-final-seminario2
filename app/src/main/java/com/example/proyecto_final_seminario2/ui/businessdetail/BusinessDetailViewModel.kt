package com.example.proyecto_final_seminario2.ui.businessdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_final_seminario2.data.places.models.PlaceDetailsItem
import com.example.proyecto_final_seminario2.data.places.repositories.PlaceDetailsRepository
import com.example.proyecto_final_seminario2.data.rating.models.LocalRatingSummary
import com.example.proyecto_final_seminario2.data.rating.repositories.RatingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class BusinessDetailUiState(
    val place: PlaceDetailsItem? = null,
    val localRatingSummary: LocalRatingSummary = LocalRatingSummary.Empty,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isUsingCachedData: Boolean = false
)

class BusinessDetailViewModel(
    private val placeDetailsRepository: PlaceDetailsRepository,
    private val ratingRepository: RatingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BusinessDetailUiState())
    val uiState: StateFlow<BusinessDetailUiState> = _uiState

    private var currentPlaceId: String? = null

    fun loadPlaceDetails(placeId: String) {
        if (placeId.isBlank()) {
            _uiState.value = BusinessDetailUiState(
                isLoading = false,
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

            val detailsResult = placeDetailsRepository.getPlaceDetails(placeId)
            val localSummary = ratingRepository
                .getLocalRatingSummary(placeId)
                .getOrDefault(LocalRatingSummary.Empty)

            detailsResult.onSuccess { placeResult ->
                _uiState.value = BusinessDetailUiState(
                    place = placeResult.place,
                    localRatingSummary = localSummary,
                    isLoading = false,
                    errorMessage = null,
                    isUsingCachedData = placeResult.isFromCache
                )
            }.onFailure { throwable ->
                _uiState.value = BusinessDetailUiState(
                    place = null,
                    localRatingSummary = localSummary,
                    isLoading = false,
                    errorMessage = throwable.message
                        ?: "No se pudieron cargar los detalles del lugar.",
                    isUsingCachedData = false
                )
            }
        }
    }

    fun retry() {
        val placeId = currentPlaceId ?: return
        loadPlaceDetails(placeId)
    }
}