package com.example.proyecto_final_seminario2.ui.rating

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.proyecto_final_seminario2.data.explorer.mock.ExplorerMockData
import com.example.proyecto_final_seminario2.data.rating.models.RatingFormData

@Composable
fun RatingFormRoute(
    businessId: String,
    onBackClick: () -> Unit,
    onSubmitClick: (RatingFormData) -> Unit,
    modifier: Modifier = Modifier
) {
    val business = ExplorerMockData.businesses.firstOrNull { it.id == businessId }

    RatingFormScreen(
        businessId = businessId,
        business = business,
        onBackClick = onBackClick,
        onSubmitClick = onSubmitClick,
        modifier = modifier
    )
}
