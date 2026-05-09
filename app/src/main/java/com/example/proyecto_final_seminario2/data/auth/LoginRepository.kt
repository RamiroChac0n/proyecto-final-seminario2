package com.example.proyecto_final_seminario2.data.auth


interface LoginRepository {
    suspend fun login(email: String, password: String): Result<Unit>
}

