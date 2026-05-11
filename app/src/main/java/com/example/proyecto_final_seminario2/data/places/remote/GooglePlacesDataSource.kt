package com.example.proyecto_final_seminario2.data.places.remote

import com.example.proyecto_final_seminario2.data.places.models.PlaceCategory
import com.example.proyecto_final_seminario2.data.places.models.PlaceSearchItem
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.model.CircularBounds
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchResolvedPhotoUriRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.api.net.SearchByTextRequest
import com.google.android.libraries.places.api.net.SearchNearbyRequest

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class GooglePlacesDataSource(
    private val placesClient: PlacesClient?
) {
    private val placeFields = listOf(
        Place.Field.ID,
        Place.Field.DISPLAY_NAME,
        Place.Field.FORMATTED_ADDRESS,
        Place.Field.LOCATION,
        Place.Field.RATING,
        Place.Field.PHOTO_METADATAS,
        Place.Field.PRIMARY_TYPE,
        Place.Field.TYPES
    )

    suspend fun searchNearby(
        latitude: Double,
        longitude: Double,
        radiusMeters: Double,
        category: PlaceCategory
    ): Result<List<PlaceSearchItem>> {
        val client = placesClient
            ?: return Result.failure(Exception("Places SDK no está configurado. Revisa PLACES_API_KEY."))

        return try {
            val center = LatLng(latitude, longitude)
            val bounds = CircularBounds.newInstance(center, radiusMeters)

            val requestBuilder = SearchNearbyRequest
                .builder(bounds, placeFields)
                .setMaxResultCount(20)

            category.googlePlaceType?.let { type ->
                requestBuilder.setIncludedTypes(listOf(type))
            }

            val response = client
                .searchNearby(requestBuilder.build())
                .awaitTask()

            val places = response.places.map { place ->
                place.toSearchItem(
                    fallbackCategory = category,
                    client = client
                )
            }

            Result.success(places)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    suspend fun searchByText(
        query: String,
        latitude: Double?,
        longitude: Double?,
        radiusMeters: Double,
        category: PlaceCategory
    ): Result<List<PlaceSearchItem>> {
        val client = placesClient
            ?: return Result.failure(Exception("Places SDK no está configurado. Revisa PLACES_API_KEY."))

        return try {
            val searchText = buildString {
                append(query.trim())

                if (category != PlaceCategory.ALL) {
                    append(" ")
                    append(category.label)
                }
            }.trim()

            val requestBuilder = SearchByTextRequest
                .builder(searchText, placeFields)
                .setMaxResultCount(20)

            if (latitude != null && longitude != null) {
                val center = LatLng(latitude, longitude)
                requestBuilder.setLocationBias(
                    CircularBounds.newInstance(center, radiusMeters)
                )
            }

            category.googlePlaceType?.let { type ->
                requestBuilder.setIncludedType(type)
            }

            val response = client
                .searchByText(requestBuilder.build())
                .awaitTask()

            val places = response.places.map { place ->
                place.toSearchItem(
                    fallbackCategory = category,
                    client = client
                )
            }

            Result.success(places)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    private suspend fun Place.toSearchItem(
        fallbackCategory: PlaceCategory,
        client: PlacesClient
    ): PlaceSearchItem {
        val placeId = id.orEmpty()
        val primaryType = primaryType
        val resolvedCategory = if (fallbackCategory == PlaceCategory.ALL) {
            PlaceCategory.fromGoogleType(primaryType)
        } else {
            fallbackCategory
        }

        val photoUri = photoMetadatas
            ?.firstOrNull()
            ?.let { metadata ->
                runCatching {
                    val request = FetchResolvedPhotoUriRequest
                        .builder(metadata)
                        .setMaxWidth(800)
                        .setMaxHeight(500)
                        .build()

                    client.fetchResolvedPhotoUri(request)
                        .awaitTask()
                        .uri
                        .toString()
                }.getOrNull()
            }

        return PlaceSearchItem(
            placeId = placeId,
            name = displayName ?: "Lugar sin nombre",
            category = resolvedCategory,
            address = formattedAddress,
            rating = rating?.toFloat(),
            photoUri = photoUri,
            latitude = location?.latitude,
            longitude = location?.longitude
        )
    }

    private suspend fun <T> Task<T>.awaitTask(): T {
        return suspendCancellableCoroutine { continuation ->
            addOnSuccessListener { result ->
                if (continuation.isActive) {
                    continuation.resume(result)
                }
            }

            addOnFailureListener { exception ->
                if (continuation.isActive) {
                    continuation.resumeWithException(exception)
                }
            }

            addOnCanceledListener {
                continuation.cancel()
            }
        }
    }
}