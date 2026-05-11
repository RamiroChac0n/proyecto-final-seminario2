package com.example.proyecto_final_seminario2.data.places.models

data class PlaceDetailsItem(
    val placeId: String,
    val name: String,
    val category: PlaceCategory,
    val address: String?,
    val rating: Float?,
    val userRatingCount: Int?,
    val phoneNumber: String?,
    val websiteUri: String?,
    val googleMapsUri: String?,
    val openingHours: List<String>,
    val photoUri: String?,
    val latitude: Double?,
    val longitude: Double?
)