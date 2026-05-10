package com.example.proyecto_final_seminario2.ui.explorer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyecto_final_seminario2.data.explorer.models.Business
import com.example.proyecto_final_seminario2.ui.explorer.components.BusinessList
import com.example.proyecto_final_seminario2.ui.explorer.components.CategoryChips
import com.example.proyecto_final_seminario2.ui.explorer.components.ExplorerHeader
import com.example.proyecto_final_seminario2.ui.explorer.components.SearchBar
import com.example.proyecto_final_seminario2.ui.explorer.components.ExplorerTopBar

@Composable
fun ExplorerScreen(
    state: ExplorerUiState,
    onCategorySelected: (com.example.proyecto_final_seminario2.data.explorer.models.BusinessCategory) -> Unit,
    onQueryChange: (String) -> Unit,
    onBusinessClick: (Business) -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf("Todos", "Restaurantes", "Farmacia", "Ferretería")
    val selectedIndex = when (state.selectedCategory) {
        com.example.proyecto_final_seminario2.data.explorer.models.BusinessCategory.ALL -> 0
        com.example.proyecto_final_seminario2.data.explorer.models.BusinessCategory.RESTAURANTS -> 1
        com.example.proyecto_final_seminario2.data.explorer.models.BusinessCategory.PHARMACY -> 2
        com.example.proyecto_final_seminario2.data.explorer.models.BusinessCategory.HARDWARE -> 3
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { ExplorerTopBar(title = "Punto Local") },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Explore,
                            contentDescription = null
                        )
                    },
                    label = { Text(text = "Explorar") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onProfileClick,
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null
                        )
                    },
                    label = { Text(text = "Perfil") }
                )
            }
        }
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)) {
            ExplorerHeader(title = "Explorar comunidad", subtitle = "Descubre negocios y servicios cerca de ti.")
            SearchBar(query = state.query, onQueryChange = onQueryChange, modifier = Modifier.padding(top = 12.dp, bottom = 12.dp))
            CategoryChips(categories = categories, selectedIndex = selectedIndex, onSelected = { idx ->
                val cat = when (idx) {
                    1 -> com.example.proyecto_final_seminario2.data.explorer.models.BusinessCategory.RESTAURANTS
                    2 -> com.example.proyecto_final_seminario2.data.explorer.models.BusinessCategory.PHARMACY
                    3 -> com.example.proyecto_final_seminario2.data.explorer.models.BusinessCategory.HARDWARE
                    else -> com.example.proyecto_final_seminario2.data.explorer.models.BusinessCategory.ALL
                }
                onCategorySelected(cat)
            }, modifier = Modifier.padding(top = 8.dp, bottom = 12.dp))

            BusinessList(
                items = state.filtered,
                onBusinessClick = onBusinessClick,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}
