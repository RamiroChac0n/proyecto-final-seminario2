package com.example.proyecto_final_seminario2.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyecto_final_seminario2.ui.explorer.ExplorerViewModel
import com.example.proyecto_final_seminario2.ui.login.LoginViewModel
import com.example.proyecto_final_seminario2.ui.register.RegisterViewModel

class AppViewModelFactory(
    private val appContainer: AppContainer
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(
                    loginRepository = appContainer.loginRepository
                ) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(
                    registerRepository = appContainer.registerRepository
                ) as T
            }

            modelClass.isAssignableFrom(ExplorerViewModel::class.java) -> {
                ExplorerViewModel(
                    placesRepository = appContainer.placesRepository,
                    locationRepository = appContainer.locationRepository
                ) as T
            }

            else -> throw IllegalArgumentException(
                "Unknown ViewModel class: ${modelClass.name}"
            )
        }
    }
}