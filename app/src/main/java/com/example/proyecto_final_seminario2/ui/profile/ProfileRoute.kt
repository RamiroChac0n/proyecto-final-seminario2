package com.example.proyecto_final_seminario2.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProfileRoute(
    onExploreClick: () -> Unit,
    onLogoutComplete: () -> Unit,
    viewModelFactory: ViewModelProvider.Factory,
    modifier: Modifier = Modifier
) {
    val viewModel: ProfileViewModel = viewModel(
        factory = viewModelFactory
    )

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    ProfileScreen(
        state = uiState,
        onExploreClick = onExploreClick,
        onRetryClick = viewModel::loadProfile,
        onLogoutClick = {
            viewModel.logout()
            onLogoutComplete()
        },
        modifier = modifier
    )
}