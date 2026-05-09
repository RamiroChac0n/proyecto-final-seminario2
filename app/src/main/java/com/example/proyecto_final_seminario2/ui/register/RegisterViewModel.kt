package com.example.proyecto_final_seminario2.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_final_seminario2.data.auth.repositories.RegisterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class RegistrationUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)

class RegisterViewModel(
    private val registerRepository: RegisterRepository? = null
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState

    fun onNameChange(name: String) {
        _uiState.value = _uiState.value.copy(name = name, errorMessage = null)
    }

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email, errorMessage = null)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password, errorMessage = null)
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword, errorMessage = null)
    }

    fun onCreateAccountClick() {
        val current = _uiState.value

        if (current.name.isBlank() || current.email.isBlank() || current.password.isBlank()) {
            _uiState.value = current.copy(errorMessage = "Todos los campos son requeridos")
            return
        }

        if (current.password != current.confirmPassword) {
            _uiState.value = current.copy(errorMessage = "Las contraseñas no coinciden")
            return
        }

        if (current.password.length < 6) {
            _uiState.value = current.copy(errorMessage = "La contraseña debe tener al menos 6 caracteres")
            return
        }

        _uiState.value = current.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                if (registerRepository != null) {
                    val result = registerRepository.register(current.name, current.email, current.password)
                    result.onSuccess {
                        _uiState.value = current.copy(isLoading = false, isSuccess = true)
                    }.onFailure { throwable ->
                        _uiState.value = current.copy(
                            isLoading = false,
                            errorMessage = throwable.message ?: "Error de registro"
                        )
                    }
                } else {
                    _uiState.value = current.copy(
                        isLoading = false,
                        errorMessage = "Backend no configurado"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = current.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Error desconocido"
                )
            }
        }
    }
}

