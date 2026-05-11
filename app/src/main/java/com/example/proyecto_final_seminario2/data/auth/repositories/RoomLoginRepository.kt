package com.example.proyecto_final_seminario2.data.auth.repositories

import com.example.proyecto_final_seminario2.data.auth.models.AuthUser
import com.example.proyecto_final_seminario2.data.auth.security.PasswordHasher
import com.example.proyecto_final_seminario2.data.auth.session.UserSessionManager
import com.example.proyecto_final_seminario2.data.local.dao.UserDao

class RoomLoginRepository(
    private val userDao: UserDao,
    private val userSessionManager: UserSessionManager
) : LoginRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Result<AuthUser> {
        return try {
            val normalizedEmail = email.trim().lowercase()

            val user = userDao.findUserByEmail(normalizedEmail)
                ?: return Result.failure(
                    Exception("No existe una cuenta con este correo")
                )

            val passwordMatches = PasswordHasher.matches(
                rawPassword = password,
                storedHash = user.passwordHash
            )

            if (!passwordMatches) {
                return Result.failure(
                    Exception("Correo o contraseña incorrectos")
                )
            }

            val authUser = AuthUser(
                id = user.id,
                name = user.name,
                email = user.email
            )

            userSessionManager.saveUser(authUser)

            Result.success(authUser)
        } catch (exception: Exception) {
            Result.failure(
                Exception(
                    exception.message ?: "No se pudo iniciar sesión"
                )
            )
        }
    }
}