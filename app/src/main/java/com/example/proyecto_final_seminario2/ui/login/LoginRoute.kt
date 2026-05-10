package com.example.proyecto_final_seminario2.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginRoute(
    onCreateAccountClick: () -> Unit,
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: LoginViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    val onLogin = {
        val current = uiState
        if (current.email == "demo@local" && current.password == "password") {
            onLoginSuccess()
        } else {
            viewModel.onLoginClick()
        }
    }

    LoginScreen(
        state = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLoginClick = onLogin,
        onCreateAccountClick = onCreateAccountClick,
        modifier = modifier
    )
}

