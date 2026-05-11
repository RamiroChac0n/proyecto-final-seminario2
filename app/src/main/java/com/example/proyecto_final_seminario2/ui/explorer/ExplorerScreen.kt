package com.example.proyecto_final_seminario2.ui.explorer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.launch

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
    var showFiltersDialog by remember {
        mutableStateOf(false)
    }

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
            ExplorerTopBar(
                title = "Punto Local",
                actions = {
                    IconButton(
                        onClick = {
                            showFiltersDialog = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.FilterList,
                            contentDescription = "Abrir filtros",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
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

            ActiveFiltersSummary(
                selectedCategory = state.selectedCategory,
                selectedRadius = state.selectedRadius,
                modifier = Modifier.padding(bottom = 10.dp)
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
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }

    if (showFiltersDialog) {
        FiltersDialog(
            categories = categories,
            selectedCategoryIndex = selectedIndex,
            selectedRadius = state.selectedRadius,
            onCategorySelected = { index ->
                val category = when (index) {
                    1 -> BusinessCategory.RESTAURANTS
                    2 -> BusinessCategory.PHARMACY
                    3 -> BusinessCategory.HARDWARE
                    else -> BusinessCategory.ALL
                }

                onCategorySelected(category)
            },
            onRadiusSelected = onRadiusSelected,
            onDismiss = {
                showFiltersDialog = false
            }
        )
    }
}

@Composable
private fun ActiveFiltersSummary(
    selectedCategory: BusinessCategory,
    selectedRadius: SearchRadiusOption,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Filtros activos",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "${selectedCategory.label} · ${selectedRadius.label}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun FiltersDialog(
    categories: List<String>,
    selectedCategoryIndex: Int,
    selectedRadius: SearchRadiusOption,
    onCategorySelected: (Int) -> Unit,
    onRadiusSelected: (SearchRadiusOption) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Filtros")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FilterSectionHeader(
                    title = "Categoría",
                    tooltipText = "Filtra los lugares por tipo de negocio o servicio."
                )

                CategoryChips(
                    categories = categories,
                    selectedIndex = selectedCategoryIndex,
                    onSelected = onCategorySelected
                )

                FilterSectionHeader(
                    title = "Distancia",
                    tooltipText = "Define cuántos kilómetros alrededor de tu ubicación se usarán para buscar lugares."
                )

                RadiusSelector(
                    selectedRadius = selectedRadius,
                    onRadiusSelected = onRadiusSelected
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = "Aplicar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = "Cerrar")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterSectionHeader(
    title: String,
    tooltipText: String,
    modifier: Modifier = Modifier
) {
    val tooltipState = rememberTooltipState()
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.width(2.dp))

        TooltipBox(
            positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
            tooltip = {
                PlainTooltip {
                    Text(text = tooltipText)
                }
            },
            state = tooltipState
        ) {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        tooltipState.show()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Información sobre $title",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}