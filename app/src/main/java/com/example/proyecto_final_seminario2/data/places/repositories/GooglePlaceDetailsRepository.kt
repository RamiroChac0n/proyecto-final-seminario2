package com.example.proyecto_final_seminario2.data.places.repositories

import com.example.proyecto_final_seminario2.data.local.dao.PlaceDao
import com.example.proyecto_final_seminario2.data.places.mappers.toEntity
import com.example.proyecto_final_seminario2.data.places.mappers.toPlaceDetailsItem
import com.example.proyecto_final_seminario2.data.places.remote.GooglePlacesDataSource

class GooglePlaceDetailsRepository(
    private val remoteDataSource: GooglePlacesDataSource,
    private val placeDao: PlaceDao
) : PlaceDetailsRepository {

    override suspend fun getPlaceDetails(
        placeId: String
    ): Result<PlaceDetailsResult> {
        val remoteResult = remoteDataSource.fetchPlaceDetails(placeId)

        return remoteResult.fold(
            onSuccess = { details ->
                placeDao.upsertPlace(details.toEntity())

                Result.success(
                    PlaceDetailsResult(
                        place = details,
                        isFromCache = false
                    )
                )
            },
            onFailure = { error ->
                val cachedPlace = placeDao.getPlaceById(placeId)

                if (cachedPlace != null) {
                    Result.success(
                        PlaceDetailsResult(
                            place = cachedPlace.toPlaceDetailsItem(),
                            isFromCache = true
                        )
                    )
                } else {
                    Result.failure(error)
                }
            }
        )
    }
}