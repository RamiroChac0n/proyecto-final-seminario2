package com.example.proyecto_final_seminario2.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_final_seminario2.data.auth.repositories.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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

        if (current.email.isBlank() || current.password.isBlank()) {
            _uiState.value = current.copy(errorMessage = "Email y contraseña son requeridos")
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

