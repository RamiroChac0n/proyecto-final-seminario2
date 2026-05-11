package com.example.proyecto_final_seminario2.data.auth.repositories

import com.example.proyecto_final_seminario2.data.auth.security.PasswordHasher
import com.example.proyecto_final_seminario2.data.local.dao.UserDao

class RoomLoginRepository(
    private val userDao: UserDao
) : LoginRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Result<Unit> {
        val normalizedEmail = email.trim().lowercase()

        val user = userDao.findUserByEmail(normalizedEmail)
            ?: return Result.failure(Exception("No existe una cuenta con este correo"))

        val passwordMatches = PasswordHasher.matches(
            rawPassword = password,
            storedHash = user.passwordHash
        )

        if (!passwordMatches) {
            return Result.failure(Exception("Correo o contraseña incorrectos"))
        }

        return Result.success(Unit)
    }
}