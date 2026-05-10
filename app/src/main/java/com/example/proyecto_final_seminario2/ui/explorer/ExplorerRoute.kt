package com.example.proyecto_final_seminario2.ui.explorer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ExplorerRoute(
    onBusinessClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val vm: ExplorerViewModel = viewModel()
    val uiState by vm.uiState.collectAsState()

    ExplorerScreen(
        state = uiState,
        onCategorySelected = vm::selectCategory,
        onQueryChange = vm::setQuery,
        onBusinessClick = { business -> onBusinessClick(business.id) },
        modifier = modifier
    )
}
