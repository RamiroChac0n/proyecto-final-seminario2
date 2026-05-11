package com.example.proyecto_final_seminario2.data.location.repositories

import com.example.proyecto_final_seminario2.data.location.models.UserLocation

interface LocationRepository {
    suspend fun getCurrentLocation(): Result<UserLocation>
}