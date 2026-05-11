package com.example.proyecto_final_seminario2.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_final_seminario2.data.profile.models.UserProfile
import com.example.proyecto_final_seminario2.data.profile.repositories.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ProfileUiState(
    val profile: UserProfile? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class ProfileViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            val result = profileRepository.getCurrentUserProfile()

            result.onSuccess { profile ->
                _uiState.value = ProfileUiState(
                    profile = profile,
                    isLoading = false,
                    errorMessage = null
                )
            }.onFailure { throwable ->
                _uiState.value = ProfileUiState(
                    profile = null,
                    isLoading = false,
                    errorMessage = throwable.message ?: "No se pudo cargar el perfil"
                )
            }
        }
    }

    fun logout() {
        profileRepository.logout()
    }
}