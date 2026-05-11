package com.example.proyecto_final_seminario2.data.places.mappers

import com.example.proyecto_final_seminario2.data.local.entities.PlaceEntity
import com.example.proyecto_final_seminario2.data.places.models.PlaceCategory
import com.example.proyecto_final_seminario2.data.places.models.PlaceDetailsItem
import com.example.proyecto_final_seminario2.data.places.models.PlaceSearchItem


fun PlaceSearchItem.toEntity(): PlaceEntity {
    return PlaceEntity(
        placeId = placeId,
        name = name,
        category = category.label,
        address = address,
        rating = rating,
        photoUri = photoUri,
        latitude = latitude,
        longitude = longitude,
        updatedAt = System.currentTimeMillis()
    )
}

fun PlaceDetailsItem.toEntity(): PlaceEntity {
    return PlaceEntity(
        placeId = placeId,
        name = name,
        category = category.label,
        address = address,
        rating = rating,
        photoUri = photoUri,
        latitude = latitude,
        longitude = longitude,
        userRatingCount = userRatingCount,
        phoneNumber = phoneNumber,
        websiteUri = websiteUri,
        googleMapsUri = googleMapsUri,
        openingHours = openingHours.joinToString(separator = "\n"),
        updatedAt = System.currentTimeMillis()
    )
}

fun PlaceEntity.toPlaceSearchItem(): PlaceSearchItem {
    return PlaceSearchItem(
        placeId = placeId,
        name = name,
        category = category.toPlaceCategory(),
        address = address,
        rating = rating,
        photoUri = photoUri,
        latitude = latitude,
        longitude = longitude
    )
}

fun PlaceEntity.toPlaceDetailsItem(): PlaceDetailsItem {
    return PlaceDetailsItem(
        placeId = placeId,
        name = name,
        category = category.toPlaceCategory(),
        address = address,
        rating = rating,
        userRatingCount = userRatingCount,
        phoneNumber = phoneNumber,
        websiteUri = websiteUri,
        googleMapsUri = googleMapsUri,
        openingHours = openingHours
            ?.split("\n")
            ?.filter { it.isNotBlank() }
            .orEmpty(),
        photoUri = photoUri,
        latitude = latitude,
        longitude = longitude
    )
}

private fun String.toPlaceCategory(): PlaceCategory {
    return when (this) {
        PlaceCategory.RESTAURANTS.label -> PlaceCategory.RESTAURANTS
        PlaceCategory.PHARMACY.label -> PlaceCategory.PHARMACY
        PlaceCategory.HARDWARE.label -> PlaceCategory.HARDWARE
        else -> PlaceCategory.ALL
    }
}