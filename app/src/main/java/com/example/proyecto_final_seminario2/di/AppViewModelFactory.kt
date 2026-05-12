package com.example.proyecto_final_seminario2.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyecto_final_seminario2.ui.businessdetail.BusinessDetailViewModel
import com.example.proyecto_final_seminario2.ui.explorer.ExplorerViewModel
import com.example.proyecto_final_seminario2.ui.login.LoginViewModel
import com.example.proyecto_final_seminario2.ui.profile.ProfileViewModel
import com.example.proyecto_final_seminario2.ui.rating.RatingFormViewModel
import com.example.proyecto_final_seminario2.ui.register.RegisterViewModel

/**
 * Fabrica unica de ViewModels.
 *
 * Permite inyectar repositorios creados en [AppContainer] sin incorporar un
 * framework de inyeccion de dependencias.
 */
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

            modelClass.isAssignableFrom(BusinessDetailViewModel::class.java) -> {
                BusinessDetailViewModel(
                    placeDetailsRepository = appContainer.placeDetailsRepository,
                    ratingRepository = appContainer.ratingRepository
                ) as T
            }

            modelClass.isAssignableFrom(RatingFormViewModel::class.java) -> {
                RatingFormViewModel(
                    ratingRepository = appContainer.ratingRepository
                ) as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(
                    profileRepository = appContainer.profileRepository
                ) as T
            }

            else -> throw IllegalArgumentException(
                "Unknown ViewModel class: ${modelClass.name}"
            )
        }
    }
}
