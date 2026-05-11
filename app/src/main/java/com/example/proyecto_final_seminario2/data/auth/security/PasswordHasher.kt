package com.example.proyecto_final_seminario2.data.auth.security

import java.security.MessageDigest

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
        return hash(rawPassword) == storedHash
    }
}