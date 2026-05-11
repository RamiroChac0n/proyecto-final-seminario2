package com.example.proyecto_final_seminario2.data.auth.repositories


import com.example.proyecto_final_seminario2.data.auth.models.AuthUser

interface LoginRepository {
    suspend fun login(
        email: String,
        password: String
    ): Result<AuthUser>
}