package com.example.proyecto_final_seminario2.data.auth.repositories

import com.example.proyecto_final_seminario2.data.auth.security.PasswordHasher
import com.example.proyecto_final_seminario2.data.local.dao.UserDao
import com.example.proyecto_final_seminario2.data.local.entities.UserEntity

/**
 * Registro local de usuarios.
 *
 * Valida correos duplicados y guarda la contraseña usando [PasswordHasher]
 * antes de insertar el usuario en Room.
 */
class RoomRegisterRepository(
    private val userDao: UserDao
) : RegisterRepository {

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<Unit> {
        val normalizedName = name.trim()
        val normalizedEmail = email.trim().lowercase()

        // La comparacion por correo normalizado evita duplicados con mayusculas o espacios.
        val existingUsers = userDao.countUsersByEmail(normalizedEmail)

        if (existingUsers > 0) {
            return Result.failure(Exception("Ya existe una cuenta con este correo"))
        }

        val user = UserEntity(
            name = normalizedName,
            email = normalizedEmail,
            passwordHash = PasswordHasher.hash(password)
        )

        return try {
            userDao.insertUser(user)
            Result.success(Unit)
        } catch (exception: Exception) {
            Result.failure(
                Exception(
                    exception.message ?: "No se pudo crear la cuenta"
                )
            )
        }
    }
}
