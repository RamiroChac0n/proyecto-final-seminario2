package com.example.proyecto_final_seminario2.ui.navigation

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyecto_final_seminario2.di.AppContainer
import com.example.proyecto_final_seminario2.di.AppViewModelFactory
import com.example.proyecto_final_seminario2.ui.businessdetail.BusinessDetailRoute
import com.example.proyecto_final_seminario2.ui.login.LoginRoute
import com.example.proyecto_final_seminario2.ui.profile.ProfileRoute
import com.example.proyecto_final_seminario2.ui.rating.RatingFormRoute
import com.example.proyecto_final_seminario2.ui.register.RegistrationRoute
import kotlinx.coroutines.launch

object AppDestination {
    const val Login = "login"
    const val Register = "register"
    const val Explorer = "explorer"
    const val Profile = "profile"
    const val BusinessDetail = "business_detail"
    const val RatingForm = "rating_form"
}

@Composable
fun AppNavGraph(
    appContainer: AppContainer,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val viewModelFactory = remember(appContainer) {
        AppViewModelFactory(appContainer)
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { scaffoldPadding ->
        NavHost(
            navController = navController,
            startDestination = AppDestination.Login,
            modifier = modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        ) {
            composable(AppDestination.Login) {
                LoginRoute(
                    onCreateAccountClick = {
                        navController.navigate(AppDestination.Register)
                    },
                    onLoginSuccess = {
                        navController.navigate(AppDestination.Explorer) {
                            popUpTo(AppDestination.Login) {
                                inclusive = true
                            }
                        }
                    },
                    viewModelFactory = viewModelFactory
                )
            }

            composable(AppDestination.Register) {
                RegistrationRoute(
                    onBackToLoginClick = {
                        navController.popBackStack()
                    },
                    onRegisterSuccess = {
                        navController.popBackStack()

                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Usuario creado correctamente"
                            )
                        }
                    },
                    viewModelFactory = viewModelFactory
                )
            }

            composable(AppDestination.Explorer) {
                com.example.proyecto_final_seminario2.ui.explorer.ExplorerRoute(
                    onBusinessClick = { businessId ->
                        navController.navigate("${AppDestination.BusinessDetail}/$businessId")
                    },
                    onProfileClick = {
                        navController.navigate(AppDestination.Profile)
                    },
                    viewModelFactory = viewModelFactory
                )
            }

            composable(AppDestination.Profile) {
                ProfileRoute(
                    onExploreClick = {
                        navController.navigate(AppDestination.Explorer) {
                            popUpTo(AppDestination.Explorer) {
                                inclusive = true
                            }
                        }
                    },
                    onLogoutClick = {
                        navController.navigate(AppDestination.Login) {
                            popUpTo(AppDestination.Explorer) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(
                route = "${AppDestination.BusinessDetail}/{businessId}",
                arguments = listOf(
                    navArgument("businessId") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                BusinessDetailRoute(
                    businessId = backStackEntry.arguments
                        ?.getString("businessId")
                        .orEmpty(),
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onRateClick = { businessId ->
                        navController.navigate(
                            "${AppDestination.RatingForm}/${Uri.encode(businessId)}"
                        )
                    }
                )
            }

            composable(
                route = "${AppDestination.RatingForm}/{businessId}",
                arguments = listOf(
                    navArgument("businessId") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                RatingFormRoute(
                    businessId = backStackEntry.arguments
                        ?.getString("businessId")
                        .orEmpty(),
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onSubmitClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}