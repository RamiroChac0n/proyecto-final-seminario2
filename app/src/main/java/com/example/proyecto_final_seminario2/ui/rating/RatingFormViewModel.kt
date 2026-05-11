package com.example.proyecto_final_seminario2.ui.rating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_final_seminario2.data.rating.models.RatingFormData
import com.example.proyecto_final_seminario2.data.rating.repositories.RatingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class RatingFormUiState(
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val isSaved: Boolean = false
)

class RatingFormViewModel(
    private val ratingRepository: RatingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RatingFormUiState())
    val uiState: StateFlow<RatingFormUiState> = _uiState

    fun saveRating(ratingFormData: RatingFormData) {
        _uiState.value = _uiState.value.copy(
            isSaving = true,
            errorMessage = null,
            isSaved = false
        )

        viewModelScope.launch {
            val result = ratingRepository.saveRating(ratingFormData)

            result.onSuccess {
                _uiState.value = RatingFormUiState(
                    isSaving = false,
                    errorMessage = null,
                    isSaved = true
                )
            }.onFailure { throwable ->
                _uiState.value = RatingFormUiState(
                    isSaving = false,
                    errorMessage = throwable.message ?: "No se pudo guardar la valoración",
                    isSaved = false
                )
            }
        }
    }
}