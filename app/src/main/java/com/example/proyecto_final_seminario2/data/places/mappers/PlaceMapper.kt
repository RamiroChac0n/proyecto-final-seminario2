package com.example.proyecto_final_seminario2.data.places.mappers

import com.example.proyecto_final_seminario2.data.local.entities.PlaceEntity
import com.example.proyecto_final_seminario2.data.places.models.PlaceCategory
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

fun PlaceEntity.toPlaceSearchItem(): PlaceSearchItem {
    return PlaceSearchItem(
        placeId = placeId,
        name = name,
        category = when (category) {
            PlaceCategory.RESTAURANTS.label -> PlaceCategory.RESTAURANTS
            PlaceCategory.PHARMACY.label -> PlaceCategory.PHARMACY
            PlaceCategory.HARDWARE.label -> PlaceCategory.HARDWARE
            else -> PlaceCategory.ALL
        },
        address = address,
        rating = rating,
        photoUri = photoUri,
        latitude = latitude,
        longitude = longitude
    )
}