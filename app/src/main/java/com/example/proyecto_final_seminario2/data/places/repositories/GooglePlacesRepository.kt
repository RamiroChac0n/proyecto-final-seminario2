package com.example.proyecto_final_seminario2.data.places.repositories

import com.example.proyecto_final_seminario2.data.local.dao.PlaceDao
import com.example.proyecto_final_seminario2.data.places.mappers.toBusiness
import com.example.proyecto_final_seminario2.data.places.mappers.toEntity
import com.example.proyecto_final_seminario2.data.places.mappers.toPlaceSearchItem
import com.example.proyecto_final_seminario2.data.places.models.PlaceCategory
import com.example.proyecto_final_seminario2.data.places.models.PlacesSearchResult
import com.example.proyecto_final_seminario2.data.places.remote.GooglePlacesDataSource

class GooglePlacesRepository(
    private val remoteDataSource: GooglePlacesDataSource,
    private val placeDao: PlaceDao
) : PlacesRepository {

    override suspend fun searchNearby(
        latitude: Double,
        longitude: Double,
        radiusMeters: Double,
        category: PlaceCategory
    ): Result<PlacesSearchResult> {
        val remoteResult = remoteDataSource.searchNearby(
            latitude = latitude,
            longitude = longitude,
            radiusMeters = radiusMeters,
            category = category
        )

        return remoteResult.fold(
            onSuccess = { remotePlaces ->
                val validPlaces = remotePlaces.filter { it.placeId.isNotBlank() }

                placeDao.upsertPlaces(
                    validPlaces.map { it.toEntity() }
                )

                Result.success(
                    PlacesSearchResult(
                        places = validPlaces.map { it.toBusiness() },
                        isFromCache = false
                    )
                )
            },
            onFailure = { error ->
                val cachedPlaces = loadCachedPlaces(category)

                if (cachedPlaces.isNotEmpty()) {
                    Result.success(
                        PlacesSearchResult(
                            places = cachedPlaces,
                            isFromCache = true
                        )
                    )
                } else {
                    Result.failure(error)
                }
            }
        )
    }

    override suspend fun searchByText(
        query: String,
        latitude: Double?,
        longitude: Double?,
        radiusMeters: Double,
        category: PlaceCategory
    ): Result<PlacesSearchResult> {
        val remoteResult = remoteDataSource.searchByText(
            query = query,
            latitude = latitude,
            longitude = longitude,
            radiusMeters = radiusMeters,
            category = category
        )

        return remoteResult.fold(
            onSuccess = { remotePlaces ->
                val validPlaces = remotePlaces.filter { it.placeId.isNotBlank() }

                placeDao.upsertPlaces(
                    validPlaces.map { it.toEntity() }
                )

                Result.success(
                    PlacesSearchResult(
                        places = validPlaces.map { it.toBusiness() },
                        isFromCache = false
                    )
                )
            },
            onFailure = { error ->
                val cachedPlaces = placeDao.searchPlaces(query)
                    .map { it.toPlaceSearchItem().toBusiness() }

                if (cachedPlaces.isNotEmpty()) {
                    Result.success(
                        PlacesSearchResult(
                            places = cachedPlaces,
                            isFromCache = true
                        )
                    )
                } else {
                    Result.failure(error)
                }
            }
        )
    }

    private suspend fun loadCachedPlaces(category: PlaceCategory) =
        if (category == PlaceCategory.ALL) {
            placeDao.getAllPlaces()
        } else {
            placeDao.getPlacesByCategory(category.label)
        }.map { entity ->
            entity.toPlaceSearchItem().toBusiness()
        }
}