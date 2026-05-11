package com.example.proyecto_final_seminario2.data.places.models

data class PlaceSearchItem(
    val placeId: String,
    val name: String,
    val category: PlaceCategory,
    val address: String?,
    val rating: Float?,
    val photoUri: String?,
    val latitude: Double?,
    val longitude: Double?
)