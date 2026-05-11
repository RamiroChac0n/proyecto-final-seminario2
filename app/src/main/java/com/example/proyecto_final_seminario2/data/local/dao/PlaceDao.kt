package com.example.proyecto_final_seminario2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.proyecto_final_seminario2.data.local.entities.PlaceEntity

@Dao
interface PlaceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPlaces(places: List<PlaceEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPlace(place: PlaceEntity)

    @Query("SELECT * FROM places ORDER BY updated_at DESC")
    suspend fun getAllPlaces(): List<PlaceEntity>

    @Query(
        """
        SELECT * FROM places
        WHERE category = :category
        ORDER BY updated_at DESC
        """
    )
    suspend fun getPlacesByCategory(category: String): List<PlaceEntity>

    @Query(
        """
        SELECT * FROM places
        WHERE name LIKE '%' || :query || '%'
           OR address LIKE '%' || :query || '%'
           OR category LIKE '%' || :query || '%'
        ORDER BY updated_at DESC
        """
    )
    suspend fun searchPlaces(query: String): List<PlaceEntity>

    @Query("SELECT * FROM places WHERE place_id = :placeId LIMIT 1")
    suspend fun getPlaceById(placeId: String): PlaceEntity?
}