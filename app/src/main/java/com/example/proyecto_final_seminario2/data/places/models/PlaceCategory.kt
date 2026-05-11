package com.example.proyecto_final_seminario2.data.places.models

import com.example.proyecto_final_seminario2.data.explorer.models.BusinessCategory

enum class PlaceCategory(
    val label: String,
    val googlePlaceType: String?
) {
    ALL(
        label = "Todos",
        googlePlaceType = null
    ),
    RESTAURANTS(
        label = "Restaurantes",
        googlePlaceType = "restaurant"
    ),
    PHARMACY(
        label = "Farmacia",
        googlePlaceType = "pharmacy"
    ),
    HARDWARE(
        label = "Ferretería",
        googlePlaceType = "hardware_store"
    );

    fun toBusinessCategory(): BusinessCategory {
        return when (this) {
            ALL -> BusinessCategory.ALL
            RESTAURANTS -> BusinessCategory.RESTAURANTS
            PHARMACY -> BusinessCategory.PHARMACY
            HARDWARE -> BusinessCategory.HARDWARE
        }
    }

    companion object {
        fun fromBusinessCategory(category: BusinessCategory): PlaceCategory {
            return when (category) {
                BusinessCategory.ALL -> ALL
                BusinessCategory.RESTAURANTS -> RESTAURANTS
                BusinessCategory.PHARMACY -> PHARMACY
                BusinessCategory.HARDWARE -> HARDWARE
            }
        }

        fun fromGoogleType(type: String?): PlaceCategory {
            return when (type) {
                "restaurant",
                "meal_takeaway",
                "cafe",
                "bakery" -> RESTAURANTS

                "pharmacy",
                "drugstore" -> PHARMACY

                "hardware_store",
                "home_goods_store" -> HARDWARE

                else -> ALL
            }
        }
    }
}