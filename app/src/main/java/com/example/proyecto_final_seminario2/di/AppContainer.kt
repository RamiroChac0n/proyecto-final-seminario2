package com.example.proyecto_final_seminario2.di

import android.content.Context
import com.example.proyecto_final_seminario2.BuildConfig
import com.example.proyecto_final_seminario2.data.auth.repositories.LoginRepository
import com.example.proyecto_final_seminario2.data.auth.repositories.RegisterRepository
import com.example.proyecto_final_seminario2.data.auth.repositories.RoomLoginRepository
import com.example.proyecto_final_seminario2.data.auth.repositories.RoomRegisterRepository
import com.example.proyecto_final_seminario2.data.local.AppDatabase
import com.example.proyecto_final_seminario2.data.location.repositories.AndroidLocationRepository
import com.example.proyecto_final_seminario2.data.location.repositories.LocationRepository
import com.example.proyecto_final_seminario2.data.places.remote.GooglePlacesDataSource
import com.example.proyecto_final_seminario2.data.places.repositories.GooglePlaceDetailsRepository
import com.example.proyecto_final_seminario2.data.places.repositories.GooglePlacesRepository
import com.example.proyecto_final_seminario2.data.places.repositories.PlaceDetailsRepository
import com.example.proyecto_final_seminario2.data.places.repositories.PlacesRepository
import com.example.proyecto_final_seminario2.data.rating.repositories.RatingRepository
import com.example.proyecto_final_seminario2.data.rating.repositories.RoomRatingRepository
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient

class AppContainer(context: Context) {

    private val appContext = context.applicationContext

    private val database: AppDatabase = AppDatabase.getDatabase(appContext)

    val loginRepository: LoginRepository = RoomLoginRepository(
        userDao = database.userDao()
    )

    val registerRepository: RegisterRepository = RoomRegisterRepository(
        userDao = database.userDao()
    )

    private val placesClient: PlacesClient? = createPlacesClient()

    private val googlePlacesDataSource = GooglePlacesDataSource(
        placesClient = placesClient
    )

    val locationRepository: LocationRepository = AndroidLocationRepository(
        context = appContext,
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(appContext)
    )

    val placesRepository: PlacesRepository = GooglePlacesRepository(
        remoteDataSource = googlePlacesDataSource,
        placeDao = database.placeDao()
    )

    val placeDetailsRepository: PlaceDetailsRepository = GooglePlaceDetailsRepository(
        remoteDataSource = googlePlacesDataSource,
        placeDao = database.placeDao()
    )

    val ratingRepository: RatingRepository = RoomRatingRepository(
        ratingDao = database.ratingDao()
    )

    private fun createPlacesClient(): PlacesClient? {
        val apiKey = BuildConfig.PLACES_API_KEY

        if (apiKey.isBlank()) {
            return null
        }

        if (!Places.isInitialized()) {
            Places.initializeWithNewPlacesApiEnabled(appContext, apiKey)
        }

        return Places.createClient(appContext)
    }
}