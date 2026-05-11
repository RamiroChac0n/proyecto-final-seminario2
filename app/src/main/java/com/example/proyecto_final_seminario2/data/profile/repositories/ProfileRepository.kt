package com.example.proyecto_final_seminario2.data.profile.repositories

import com.example.proyecto_final_seminario2.data.profile.models.UserProfile

interface ProfileRepository {

    suspend fun getCurrentUserProfile(): Result<UserProfile>

    fun logout()
}