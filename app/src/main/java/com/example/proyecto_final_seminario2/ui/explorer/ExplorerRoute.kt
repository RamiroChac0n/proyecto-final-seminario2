package com.example.proyecto_final_seminario2.ui.explorer

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ExplorerRoute(
    onBusinessClick: (String) -> Unit,
    onProfileClick: () -> Unit,
    viewModelFactory: ViewModelProvider.Factory,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val viewModel: ExplorerViewModel = viewModel(
        factory = viewModelFactory
    )

    val uiState by viewModel.uiState.collectAsState()

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        viewModel.onLocationPermissionResult(granted)
    }

    LaunchedEffect(Unit) {
        val finePermissionGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarsePermissionGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasPermission = finePermissionGranted || coarsePermissionGranted

        if (hasPermission) {
            viewModel.onLocationPermissionResult(true)
        } else {
            viewModel.onLocationPermissionResult(false)
        }
    }

    ExplorerScreen(
        state = uiState,
        onCategorySelected = viewModel::selectCategory,
        onRadiusSelected = viewModel::selectRadius,
        onQueryChange = viewModel::setQuery,
        onSearchSubmit = viewModel::submitSearch,
        onRetryClick = viewModel::retry,
        onRequestLocationPermissionClick = {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        },
        onBusinessClick = { business ->
            onBusinessClick(business.placeId)
        },
        onProfileClick = onProfileClick,
        modifier = modifier
    )
}