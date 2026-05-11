package com.example.proyecto_final_seminario2.ui.rating

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_final_seminario2.data.explorer.models.Business

@Composable
fun RatingFormRoute(
    businessId: String,
    business: Business? = null,
    onBackClick: () -> Unit,
    onSubmitSuccess: () -> Unit,
    viewModelFactory: ViewModelProvider.Factory,
    modifier: Modifier = Modifier
) {
    val viewModel: RatingFormViewModel = viewModel(
        factory = viewModelFactory
    )

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            onSubmitSuccess()
        }
    }

    RatingFormScreen(
        businessId = businessId,
        business = business,
        onBackClick = onBackClick,
        onSubmitClick = viewModel::saveRating,
        modifier = modifier
    )
}