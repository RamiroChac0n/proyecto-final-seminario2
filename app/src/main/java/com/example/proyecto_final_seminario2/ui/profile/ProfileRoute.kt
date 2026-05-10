package com.example.proyecto_final_seminario2.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProfileRoute(
    onExploreClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ProfileScreen(
        onExploreClick = onExploreClick,
        onLogoutClick = onLogoutClick,
        modifier = modifier
    )
}
