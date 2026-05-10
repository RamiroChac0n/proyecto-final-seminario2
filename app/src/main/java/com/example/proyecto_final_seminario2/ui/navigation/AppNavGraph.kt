package com.example.proyecto_final_seminario2.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyecto_final_seminario2.ui.businessdetail.BusinessDetailRoute
import com.example.proyecto_final_seminario2.ui.login.LoginRoute
import com.example.proyecto_final_seminario2.ui.register.RegistrationRoute

object AppDestination {
    const val Login = "login"
    const val Register = "register"
    const val Explorer = "explorer"
    const val BusinessDetail = "business_detail"
}

@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

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
            com.example.proyecto_final_seminario2.ui.explorer.ExplorerRoute(
                onBusinessClick = { businessId ->
                    navController.navigate("${AppDestination.BusinessDetail}/$businessId")
                }
            )
        }
        composable(
            route = "${AppDestination.BusinessDetail}/{businessId}",
            arguments = listOf(navArgument("businessId") { type = NavType.StringType })
        ) { backStackEntry ->
            BusinessDetailRoute(
                businessId = backStackEntry.arguments?.getString("businessId").orEmpty(),
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

