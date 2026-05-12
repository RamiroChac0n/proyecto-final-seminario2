package com.example.proyecto_final_seminario2.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_final_seminario2.data.auth.repositories.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

<<<<<<< Updated upstream
=======

/**
 * Maneja el estado del formulario de login.
 *
 * Valida campos basicos antes de delegar la autenticacion al repositorio.
 */
>>>>>>> Stashed changes
class LoginViewModel(
    private val loginRepository: LoginRepository? = null
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email, errorMessage = null)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password, errorMessage = null)
    }

    fun onLoginClick() {
        val current = _uiState.value

<<<<<<< Updated upstream
        if (current.email.isBlank() || current.password.isBlank()) {
            _uiState.value = current.copy(errorMessage = "Email y contraseña son requeridos")
=======
        val email = current.email.trim()
        val password = current.password

        // Validaciones de interfaz: evitan llamadas innecesarias al repositorio.
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = current.copy(
                errorMessage = "Correo y contraseña son requeridos",
                isSuccess = false
            )
>>>>>>> Stashed changes
            return
        }

        _uiState.value = current.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                if (loginRepository != null) {
                    val result = loginRepository.login(current.email, current.password)
                    result.onSuccess {
                        _uiState.value = current.copy(isLoading = false, errorMessage = null)
                    }.onFailure { throwable ->
                        _uiState.value = current.copy(
                            isLoading = false,
                            errorMessage = throwable.message ?: "Error de autenticación"
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

<<<<<<< Updated upstream
=======
    fun clearSuccess() {
        // Permite que la ruta consuma el exito de login sin volver a navegar.
        _uiState.value = _uiState.value.copy(
            isSuccess = false
        )
    }
}
>>>>>>> Stashed changes
