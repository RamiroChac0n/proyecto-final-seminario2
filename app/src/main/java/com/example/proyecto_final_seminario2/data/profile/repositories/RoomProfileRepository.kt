package com.example.proyecto_final_seminario2.data.profile.repositories

import com.example.proyecto_final_seminario2.data.auth.models.AuthUser
import com.example.proyecto_final_seminario2.data.auth.session.UserSessionManager
import com.example.proyecto_final_seminario2.data.local.dao.RatingDao
import com.example.proyecto_final_seminario2.data.local.dao.UserDao
import com.example.proyecto_final_seminario2.data.local.entities.RatingEntity
import com.example.proyecto_final_seminario2.data.profile.models.ProfileReviewItem
import com.example.proyecto_final_seminario2.data.profile.models.UserProfile
import com.example.proyecto_final_seminario2.data.rating.models.HighlightedQuality
import com.example.proyecto_final_seminario2.data.rating.models.WaitTimeOption

/**
 * Repositorio del perfil del usuario actual.
 *
 * Combina datos de sesion, informacion guardada en Room y valoraciones hechas
 * por el usuario para construir la pantalla de perfil.
 */
class RoomProfileRepository(
    private val userDao: UserDao,
    private val ratingDao: RatingDao,
    private val userSessionManager: UserSessionManager
) : ProfileRepository {

    override suspend fun getCurrentUserProfile(): Result<UserProfile> {
        return try {
            val sessionUser = userSessionManager.getCurrentUser()
                ?: return Result.failure(Exception("No hay una sesión activa"))

            val userEntity = userDao.getUserById(sessionUser.id)
                ?: return Result.failure(Exception("No se encontró el usuario actual"))

            val user = AuthUser(
                id = userEntity.id,
                name = userEntity.name,
                email = userEntity.email
            )

            // El historial se arma desde las valoraciones locales asociadas al usuario.
            val reviews = ratingDao.getRatingsByUserId(user.id)
                .map { rating ->
                    rating.toProfileReviewItem()
                }

            Result.success(
                UserProfile(
                    user = user,
                    totalReviews = reviews.size,
                    reviews = reviews
                )
            )
        } catch (exception: Exception) {
            Result.failure(
                Exception(
                    exception.message ?: "No se pudo cargar el perfil"
                )
            )
        }
    }

    override fun logout() {
        // Cerrar sesion solo borra la sesion activa; los datos locales permanecen.
        userSessionManager.clearSession()
    }

    private fun RatingEntity.toProfileReviewItem(): ProfileReviewItem {
        return ProfileReviewItem(
            id = id,
            placeId = businessId,
            placeName = placeName ?: "Lugar sin nombre",
            placeCategory = placeCategory ?: "Sin categoría",
            placeAddress = placeAddress,
            paidPrice = paidPrice,
            visitDate = visitDate,
            serviceRating = serviceRating,
            attentionRating = attentionRating,
            satisfactionRating = satisfactionRating,
            waitTime = waitTime.toWaitTimeLabel(),
            recommends = recommends,
            highlightedQualities = highlightedQualities
                .split(",")
                .map { it.trim() }
                .filter { it.isNotBlank() }
                .map { it.toHighlightedQualityLabel() },
            createdAt = createdAt
        )
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
