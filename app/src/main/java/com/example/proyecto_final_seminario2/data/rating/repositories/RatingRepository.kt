package com.example.proyecto_final_seminario2.data.rating.repositories

import com.example.proyecto_final_seminario2.data.rating.models.LocalRatingSummary
import com.example.proyecto_final_seminario2.data.rating.models.RatingFormData

interface RatingRepository {

    suspend fun saveRating(ratingFormData: RatingFormData): Result<Unit>

    suspend fun getLocalRatingSummary(businessId: String): Result<LocalRatingSummary>
}