package com.example.proyecto_final_seminario2.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

enum class AppTab {
    Explore,
    Profile
}

@Composable
fun AppBottomBar(
    selectedTab: AppTab,
    onExploreClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 3.dp
    ) {
        NavigationBarItem(
            selected = selectedTab == AppTab.Explore,
            onClick = onExploreClick,
            icon = {
                Icon(
                    imageVector = Icons.Filled.Explore,
                    contentDescription = "Ir a explorar"
                )
            },
            label = {
                Text(text = "Explorar")
            },
            colors = bottomBarItemColors()
        )

        NavigationBarItem(
            selected = selectedTab == AppTab.Profile,
            onClick = onProfileClick,
            icon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Ir a perfil"
                )
            },
            label = {
                Text(text = "Perfil")
            },
            colors = bottomBarItemColors()
        )
    }
}

@Composable
private fun bottomBarItemColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = MaterialTheme.colorScheme.primary,
    selectedTextColor = MaterialTheme.colorScheme.primary,
    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
)