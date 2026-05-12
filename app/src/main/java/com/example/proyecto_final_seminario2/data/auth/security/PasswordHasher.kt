package com.example.proyecto_final_seminario2.data.auth.security

import java.security.MessageDigest

/**
 * Utilidad para generar y verificar hashes de contraseña.
 *
 * Nota: SHA-256 con salt fijo es suficiente para este proyecto local, pero en
 * produccion convendria usar un algoritmo especializado como bcrypt, scrypt o Argon2.
 */
object PasswordHasher {

    private const val APP_SALT = "punto-local-local-auth-salt"

    fun hash(password: String): String {
        val input = "$APP_SALT:$password"
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(input.toByteArray())

        return bytes.joinToString(separator = "") { byte ->
            "%02x".format(byte)
        }
    }

    fun matches(
        rawPassword: String,
        storedHash: String
    ): Boolean {
        // Compara el hash calculado de la entrada con el hash persistido.
        return hash(rawPassword) == storedHash
    }
}
