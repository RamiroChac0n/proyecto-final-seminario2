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
    val errorMessage: String? = null
)

class RegisterViewModel(
    private val registerRepository: RegisterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState

    fun onNameChange(name: String) {
        _uiState.value = _uiState.value.copy(
            name = name,
            errorMessage = null
        )
    }

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email,
            errorMessage = null
        )
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            errorMessage = null
        )
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(
            confirmPassword = confirmPassword,
            errorMessage = null
        )
    }

    fun onCreateAccountClick(
        onSuccess: () -> Unit
    ) {
        val current = _uiState.value

        val name = current.name.trim()
        val email = current.email.trim()
        val password = current.password
        val confirmPassword = current.confirmPassword

        if (
            name.isBlank() ||
            email.isBlank() ||
            password.isBlank() ||
            confirmPassword.isBlank()
        ) {
            _uiState.value = current.copy(
                errorMessage = "Todos los campos son requeridos"
            )
            return
        }

        if (!email.contains("@")) {
            _uiState.value = current.copy(
                errorMessage = "Ingresa un correo válido"
            )
            return
        }

        if (password != confirmPassword) {
            _uiState.value = current.copy(
                errorMessage = "Las contraseñas no coinciden"
            )
            return
        }

        if (password.length < 6) {
            _uiState.value = current.copy(
                errorMessage = "La contraseña debe tener al menos 6 caracteres"
            )
            return
        }

        _uiState.value = current.copy(
            isLoading = true,
            errorMessage = null
        )

        viewModelScope.launch {
            val result = registerRepository.register(
                name = name,
                email = email,
                password = password
            )

            result.onSuccess {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = null
                )

                onSuccess()
            }.onFailure { throwable ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = throwable.message ?: "Error de registro"
                )
            }
        }
    }
}