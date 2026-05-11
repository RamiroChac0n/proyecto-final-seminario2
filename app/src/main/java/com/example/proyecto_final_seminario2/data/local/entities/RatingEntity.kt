package com.example.proyecto_final_seminario2.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ratings")
data class RatingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "business_id")
    val businessId: String,

    @ColumnInfo(name = "place_name")
    val placeName: String?,

    @ColumnInfo(name = "place_category")
    val placeCategory: String?,

    @ColumnInfo(name = "place_address")
    val placeAddress: String?,

    @ColumnInfo(name = "paid_price")
    val paidPrice: String,

    @ColumnInfo(name = "visit_date")
    val visitDate: String,

    @ColumnInfo(name = "service_rating")
    val serviceRating: Int,

    @ColumnInfo(name = "attention_rating")
    val attentionRating: Int,

    @ColumnInfo(name = "satisfaction_rating")
    val satisfactionRating: Int,

    @ColumnInfo(name = "wait_time")
    val waitTime: String,

    @ColumnInfo(name = "recommends")
    val recommends: Boolean,

    @ColumnInfo(name = "highlighted_qualities")
    val highlightedQualities: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)