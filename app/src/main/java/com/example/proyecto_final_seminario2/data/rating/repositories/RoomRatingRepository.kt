package com.example.proyecto_final_seminario2.data.rating.repositories

import com.example.proyecto_final_seminario2.data.auth.session.UserSessionManager
import com.example.proyecto_final_seminario2.data.local.dao.PlaceDao
import com.example.proyecto_final_seminario2.data.local.dao.RatingDao
import com.example.proyecto_final_seminario2.data.local.entities.RatingEntity
import com.example.proyecto_final_seminario2.data.rating.models.HighlightedQuality
import com.example.proyecto_final_seminario2.data.rating.models.LocalRatingSummary
import com.example.proyecto_final_seminario2.data.rating.models.RatingFormData
import com.example.proyecto_final_seminario2.data.rating.models.WaitTimeOption

class RoomRatingRepository(
    private val ratingDao: RatingDao,
    private val placeDao: PlaceDao,
    private val userSessionManager: UserSessionManager
) : RatingRepository {

    override suspend fun saveRating(
        ratingFormData: RatingFormData
    ): Result<Unit> {
        return try {
            val currentUser = userSessionManager.getCurrentUser()
                ?: return Result.failure(Exception("Debes iniciar sesión para guardar una valoración"))

            val place = placeDao.getPlaceById(ratingFormData.businessId)

            val entity = RatingEntity(
                userId = currentUser.id,
                businessId = ratingFormData.businessId,
                placeName = place?.name,
                placeCategory = place?.category,
                placeAddress = place?.address,
                paidPrice = ratingFormData.paidPrice,
                visitDate = ratingFormData.visitDate,
                serviceRating = ratingFormData.serviceRating,
                attentionRating = ratingFormData.attentionRating,
                satisfactionRating = ratingFormData.satisfactionRating,
                waitTime = ratingFormData.waitTime.name,
                recommends = ratingFormData.recommends,
                highlightedQualities = ratingFormData.highlightedQualities.joinToString(
                    separator = ","
                ) { quality ->
                    quality.name
                }
            )

            ratingDao.insertRating(entity)

            Result.success(Unit)
        } catch (exception: Exception) {
            Result.failure(
                Exception(
                    exception.message ?: "No se pudo guardar la valoración"
                )
            )
        }
    }

    override suspend fun getLocalRatingSummary(
        businessId: String
    ): Result<LocalRatingSummary> {
        return try {
            val ratings = ratingDao.getRatingsByBusinessId(businessId)

            if (ratings.isEmpty()) {
                return Result.success(LocalRatingSummary.Empty)
            }

            val total = ratings.size

            val averageService = ratings
                .map { it.serviceRating }
                .average()
                .toFloat()

            val averageAttention = ratings
                .map { it.attentionRating }
                .average()
                .toFloat()

            val averageSatisfaction = ratings
                .map { it.satisfactionRating }
                .average()
                .toFloat()

            val recommendationPercent = (
                    ratings.count { it.recommends }.toFloat() / total.toFloat() * 100
                    ).toInt()

            val mostCommonWaitTime = ratings
                .groupBy { it.waitTime }
                .maxByOrNull { entry -> entry.value.size }
                ?.key
                ?.toWaitTimeLabel()

            val highlightedQualities = ratings
                .flatMap { rating ->
                    rating.highlightedQualities
                        .split(",")
                        .map { it.trim() }
                        .filter { it.isNotBlank() }
                }
                .groupingBy { it }
                .eachCount()
                .entries
                .sortedByDescending { it.value }
                .take(3)
                .map { entry ->
                    entry.key.toHighlightedQualityLabel()
                }

            Result.success(
                LocalRatingSummary(
                    totalRatings = total,
                    averageServiceRating = averageService,
                    averageAttentionRating = averageAttention,
                    averageSatisfactionRating = averageSatisfaction,
                    recommendationPercent = recommendationPercent,
                    mostCommonWaitTime = mostCommonWaitTime,
                    highlightedQualities = highlightedQualities
                )
            )
        } catch (exception: Exception) {
            Result.failure(
                Exception(
                    exception.message ?: "No se pudo obtener el resumen de valoraciones"
                )
            )
        }
    }

    private fun String.toWaitTimeLabel(): String {
        return runCatching {
            WaitTimeOption.valueOf(this).label
        }.getOrDefault(this)
    }

    private fun String.toHighlightedQualityLabel(): String {
        return runCatching {
            HighlightedQuality.valueOf(this).label
        }.getOrDefault(this)
    }
}