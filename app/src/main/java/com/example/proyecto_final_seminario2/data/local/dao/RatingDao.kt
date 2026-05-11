package com.example.proyecto_final_seminario2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.proyecto_final_seminario2.data.local.entities.RatingEntity

@Dao
interface RatingDao {

    @Insert
    suspend fun insertRating(rating: RatingEntity)

    @Query(
        """
        SELECT * FROM ratings
        WHERE business_id = :businessId
        ORDER BY created_at DESC
        """
    )
    suspend fun getRatingsByBusinessId(businessId: String): List<RatingEntity>

    @Query(
        """
        SELECT * FROM ratings
        ORDER BY created_at DESC
        """
    )
    suspend fun getAllRatings(): List<RatingEntity>

    @Query("DELETE FROM ratings")
    suspend fun deleteAllRatings()
}