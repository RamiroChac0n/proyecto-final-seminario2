package com.example.proyecto_final_seminario2.ui.businessdetail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.proyecto_final_seminario2.data.explorer.mock.ExplorerMockData

@Composable
fun BusinessDetailRoute(
    businessId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val business = ExplorerMockData.businesses.firstOrNull { it.id == businessId }

    BusinessDetailScreen(
        business = business,
        onBackClick = onBackClick,
        modifier = modifier
    )
}
