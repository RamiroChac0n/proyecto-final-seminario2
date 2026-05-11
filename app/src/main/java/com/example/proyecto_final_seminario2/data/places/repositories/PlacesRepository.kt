package com.example.proyecto_final_seminario2.data.places.repositories

import com.example.proyecto_final_seminario2.data.explorer.models.Business
import com.example.proyecto_final_seminario2.data.places.models.PlaceCategory
import com.example.proyecto_final_seminario2.data.places.models.PlacesSearchResult

interface PlacesRepository {

    suspend fun searchNearby(
        latitude: Double,
        longitude: Double,
        radiusMeters: Double,
        category: PlaceCategory
    ): Result<PlacesSearchResult>

    suspend fun searchByText(
        query: String,
        latitude: Double?,
        longitude: Double?,
        radiusMeters: Double,
        category: PlaceCategory
    ): Result<PlacesSearchResult>
}