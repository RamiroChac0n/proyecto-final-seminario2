package com.example.proyecto_final_seminario2.data.places.mappers

import com.example.proyecto_final_seminario2.data.explorer.models.Business
import com.example.proyecto_final_seminario2.data.places.models.PlaceCategory
import com.example.proyecto_final_seminario2.data.places.models.PlaceSearchItem

fun PlaceSearchItem.toBusiness(): Business {
    val safeRating = rating ?: 0f
    val displayRating = if (safeRating <= 0f) 0f else safeRating

    return Business(
        id = placeId,
        placeId = placeId,
        name = name,
        category = category.toBusinessCategory(),
        locationCode = address ?: "Ubicación disponible",
        distance = "Cerca de ti",
        rating = displayRating,
        recommendationPercent = 0,
        imageUrl = photoUri,
        tags = buildTags(category),
        address = address
    )
}

private fun buildTags(category: PlaceCategory): List<String> {
    return when (category) {
        PlaceCategory.RESTAURANTS -> listOf("Restaurante", "Servicio local")
        PlaceCategory.PHARMACY -> listOf("Farmacia", "Servicio local")
        PlaceCategory.HARDWARE -> listOf("Ferretería", "Servicio local")
        PlaceCategory.ALL -> listOf("Servicio local")
    }
}