package com.example.proyecto_final_seminario2.ui.login

data class LoginState(
	val email: String = "",
	val password: String = "",
	val isLoading: Boolean = false,
	val errorMessage: String? = null,
	val isSuccess: Boolean = false
)

