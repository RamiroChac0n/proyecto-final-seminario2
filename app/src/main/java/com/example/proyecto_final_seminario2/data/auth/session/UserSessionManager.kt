package com.example.proyecto_final_seminario2.data.auth.session

import android.content.Context
import com.example.proyecto_final_seminario2.data.auth.models.AuthUser
import androidx.core.content.edit

class UserSessionManager(context: Context) {

    private val preferences = context.applicationContext.getSharedPreferences(
        SESSION_PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )

    fun saveUser(user: AuthUser) {
        preferences.edit {
            putInt(KEY_USER_ID, user.id)
                .putString(KEY_USER_NAME, user.name)
                .putString(KEY_USER_EMAIL, user.email)
        }
    }

    fun getCurrentUser(): AuthUser? {
        val id = preferences.getInt(KEY_USER_ID, NO_USER_ID)

        if (id == NO_USER_ID) {
            return null
        }

        val name = preferences.getString(KEY_USER_NAME, null)
        val email = preferences.getString(KEY_USER_EMAIL, null)

        if (name.isNullOrBlank() || email.isNullOrBlank()) {
            return null
        }

        return AuthUser(
            id = id,
            name = name,
            email = email
        )
    }

    fun isLoggedIn(): Boolean {
        return getCurrentUser() != null
    }

    fun clearSession() {
        preferences.edit {
            clear()
        }
    }

    companion object {
        private const val SESSION_PREFERENCES_NAME = "user_session"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_EMAIL = "user_email"
        private const val NO_USER_ID = -1
    }
}