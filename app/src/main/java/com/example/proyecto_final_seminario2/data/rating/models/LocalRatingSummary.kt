package com.example.proyecto_final_seminario2.data.rating.models

data class LocalRatingSummary(
    val totalRatings: Int = 0,
    val averageServiceRating: Float = 0f,
    val averageAttentionRating: Float = 0f,
    val averageSatisfactionRating: Float = 0f,
    val recommendationPercent: Int = 0,
    val mostCommonWaitTime: String? = null,
    val highlightedQualities: List<String> = emptyList()
) {
    val hasRatings: Boolean
        get() = totalRatings > 0

    companion object {
        val Empty = LocalRatingSummary()
    }
}