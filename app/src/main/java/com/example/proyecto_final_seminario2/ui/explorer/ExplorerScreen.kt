package com.example.proyecto_final_seminario2.ui.explorer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyecto_final_seminario2.data.explorer.models.Business
import com.example.proyecto_final_seminario2.data.explorer.models.BusinessCategory
import com.example.proyecto_final_seminario2.data.places.models.SearchRadiusOption
import com.example.proyecto_final_seminario2.ui.explorer.components.BusinessList
import com.example.proyecto_final_seminario2.ui.explorer.components.CategoryChips
import com.example.proyecto_final_seminario2.ui.explorer.components.ExplorerHeader
import com.example.proyecto_final_seminario2.ui.explorer.components.ExplorerTopBar
import com.example.proyecto_final_seminario2.ui.explorer.components.LocationPermissionCard
import com.example.proyecto_final_seminario2.ui.explorer.components.RadiusSelector
import com.example.proyecto_final_seminario2.ui.explorer.components.SearchBar
import com.example.proyecto_final_seminario2.ui.navigation.AppBottomBar
import com.example.proyecto_final_seminario2.ui.navigation.AppTab

@Composable
fun ExplorerScreen(
    state: ExplorerUiState,
    onCategorySelected: (BusinessCategory) -> Unit,
    onRadiusSelected: (SearchRadiusOption) -> Unit,
    onQueryChange: (String) -> Unit,
    onSearchSubmit: () -> Unit,
    onRetryClick: () -> Unit,
    onRequestLocationPermissionClick: () -> Unit,
    onBusinessClick: (Business) -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf("Todos", "Restaurantes", "Farmacia", "Ferretería")

    val selectedIndex = when (state.selectedCategory) {
        BusinessCategory.ALL -> 0
        BusinessCategory.RESTAURANTS -> 1
        BusinessCategory.PHARMACY -> 2
        BusinessCategory.HARDWARE -> 3
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            ExplorerTopBar(title = "Punto Local")
        },
        bottomBar = {
            AppBottomBar(
                selectedTab = AppTab.Explore,
                onExploreClick = {},
                onProfileClick = onProfileClick
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            ExplorerHeader(
                title = "Explorar comunidad",
                subtitle = "Descubre negocios y servicios cerca de ti."
            )

            SearchBar(
                query = state.query,
                onQueryChange = onQueryChange,
                onSearchSubmit = onSearchSubmit,
                modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)
            )

            CategoryChips(
                categories = categories,
                selectedIndex = selectedIndex,
                onSelected = { index ->
                    val category = when (index) {
                        1 -> BusinessCategory.RESTAURANTS
                        2 -> BusinessCategory.PHARMACY
                        3 -> BusinessCategory.HARDWARE
                        else -> BusinessCategory.ALL
                    }

                    onCategorySelected(category)
                },
                modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
            )

            RadiusSelector(
                selectedRadius = state.selectedRadius,
                onRadiusSelected = onRadiusSelected,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            if (state.isUsingCachedData) {
                Text(
                    text = "Mostrando resultados guardados.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            if (!state.hasLocationPermission) {
                LocationPermissionCard(
                    onRequestPermissionClick = onRequestLocationPermissionClick,
                    modifier = Modifier.padding(top = 12.dp)
                )
                return@Column
            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 24.dp)
                )
                return@Column
            }

            if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )

                TextButton(
                    onClick = onRetryClick,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(text = "Reintentar")
                }

                return@Column
            }

            if (state.filtered.isEmpty()) {
                Text(
                    text = "No encontramos lugares para esta búsqueda.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 24.dp)
                )
                return@Column
            }

            BusinessList(
                items = state.filtered,
                onBusinessClick = onBusinessClick,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}

