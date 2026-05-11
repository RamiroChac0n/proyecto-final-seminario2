package com.example.proyecto_final_seminario2.data.profile.models

data class ProfileReviewItem(
    val id: Int,
    val placeId: String,
    val placeName: String,
    val placeCategory: String,
    val placeAddress: String?,
    val paidPrice: String,
    val visitDate: String,
    val serviceRating: Int,
    val attentionRating: Int,
    val satisfactionRating: Int,
    val waitTime: String,
    val recommends: Boolean,
    val highlightedQualities: List<String>,
    val createdAt: Long
)