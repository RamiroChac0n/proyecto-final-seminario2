package com.example.proyecto_final_seminario2.data.places.repositories

import com.example.proyecto_final_seminario2.data.places.models.PlaceDetailsItem

data class PlaceDetailsResult(
    val place: PlaceDetailsItem,
    val isFromCache: Boolean
)

interface PlaceDetailsRepository {
    suspend fun getPlaceDetails(placeId: String): Result<PlaceDetailsResult>
}