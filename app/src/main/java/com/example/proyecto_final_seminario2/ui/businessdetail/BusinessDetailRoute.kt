package com.example.proyecto_final_seminario2.ui.businessdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BusinessDetailRoute(
    businessId: String,
    onBackClick: () -> Unit,
    onRateClick: (String) -> Unit,
    viewModelFactory: ViewModelProvider.Factory,
    modifier: Modifier = Modifier
) {
    val viewModel: BusinessDetailViewModel = viewModel(
        factory = viewModelFactory
    )

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(businessId) {
        viewModel.loadPlaceDetails(businessId)
    }

    BusinessDetailScreen(
        state = uiState,
        onBackClick = onBackClick,
        onRateClick = {
            onRateClick(businessId)
        },
        onRetryClick = viewModel::retry,
        modifier = modifier
    )
}