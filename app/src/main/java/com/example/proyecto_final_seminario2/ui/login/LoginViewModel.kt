package com.example.proyecto_final_seminario2.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_final_seminario2.data.auth.repositories.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email,
            errorMessage = null,
            isSuccess = false
        )
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            errorMessage = null,
            isSuccess = false
        )
    }

    fun onLoginClick() {
        val current = _uiState.value
        val email = current.email.trim()
        val password = current.password

        if (email.isBlank() || password.isBlank()) {
            _uiState.value = current.copy(
                errorMessage = "Correo y contraseña son requeridos"
            )
            return
        }

        if (!email.contains("@")) {
            _uiState.value = current.copy(
                errorMessage = "Ingresa un correo válido"
            )
            return
        }

        _uiState.value = current.copy(
            isLoading = true,
            errorMessage = null,
            isSuccess = false
        )

        viewModelScope.launch {
            val result = loginRepository.login(
                email = email,
                password = password
            )

            result.onSuccess {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = null,
                    isSuccess = true
                )
            }.onFailure { throwable ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = throwable.message ?: "Error de autenticación",
                    isSuccess = false
                )
            }
        }
    }
}