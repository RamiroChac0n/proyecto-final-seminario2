package com.example.proyecto_final_seminario2.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places")
data class PlaceEntity(
    @PrimaryKey
    @ColumnInfo(name = "place_id")
    val placeId: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "address")
    val address: String?,

    @ColumnInfo(name = "rating")
    val rating: Float?,

    @ColumnInfo(name = "photo_uri")
    val photoUri: String?,

    @ColumnInfo(name = "latitude")
    val latitude: Double?,

    @ColumnInfo(name = "longitude")
    val longitude: Double?,

    @ColumnInfo(name = "user_rating_count")
    val userRatingCount: Int? = null,

    @ColumnInfo(name = "phone_number")
    val phoneNumber: String? = null,

    @ColumnInfo(name = "website_uri")
    val websiteUri: String? = null,

    @ColumnInfo(name = "google_maps_uri")
    val googleMapsUri: String? = null,

    @ColumnInfo(name = "opening_hours")
    val openingHours: String? = null,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)