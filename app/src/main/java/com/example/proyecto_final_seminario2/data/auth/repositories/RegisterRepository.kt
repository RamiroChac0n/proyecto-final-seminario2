package com.example.proyecto_final_seminario2.data.auth.repositories

interface RegisterRepository {
    suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<Unit>
}