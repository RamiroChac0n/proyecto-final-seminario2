package com.example.proyecto_final_seminario2.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyecto_final_seminario2.ui.login.LoginRoute
import com.example.proyecto_final_seminario2.ui.register.RegistrationRoute

/**
 * Rutas internas usadas por Navigation Compose.
 */
object AppDestination {
    const val Login = "login"
    const val Register = "register"
    const val Explorer = "explorer"
}

@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

<<<<<<< Updated upstream
    NavHost(
        navController = navController,
        startDestination = AppDestination.Login,
        modifier = modifier.fillMaxSize()
    ) {
        composable(AppDestination.Login) {
            LoginRoute(
                onCreateAccountClick = {
                    navController.navigate(AppDestination.Register)
                },
                onLoginSuccess = {
                    navController.navigate(AppDestination.Explorer) {
                        popUpTo(AppDestination.Login) { inclusive = true }
                    }
                }
            )
        }
        composable(AppDestination.Register) {
            RegistrationRoute(
                onBackToLoginClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(AppDestination.Explorer) {
            com.example.proyecto_final_seminario2.ui.explorer.ExplorerRoute()
=======
    val viewModelFactory = remember(appContainer) {
        AppViewModelFactory(appContainer)
    }

    // La sesion guardada decide si se entra directo al explorador o al login.
    val startDestination = remember(appContainer) {
        if (appContainer.userSessionManager.isLoggedIn()) {
            AppDestination.Explorer
        } else {
            AppDestination.Login
>>>>>>> Stashed changes
        }
    }
}


<<<<<<< Updated upstream
=======
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
                    onLogoutComplete = {
                        navController.navigate(AppDestination.Login) {
                            popUpTo(0) {
                                inclusive = true
                            }
                        }
                    },
                    viewModelFactory = viewModelFactory
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
                // Bandera temporal para refrescar valoraciones al volver del formulario.
                val ratingSaved by backStackEntry.savedStateHandle
                    .getStateFlow("rating_saved", false)
                    .collectAsState()

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
                    },
                    viewModelFactory = viewModelFactory,
                    refreshRatings = ratingSaved,
                    onRefreshRatingsConsumed = {
                        backStackEntry.savedStateHandle["rating_saved"] = false
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
                    onSubmitSuccess = {
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("rating_saved", true)

                        navController.popBackStack()
                    },
                    viewModelFactory = viewModelFactory
                )
            }
        }
    }
}
>>>>>>> Stashed changes
