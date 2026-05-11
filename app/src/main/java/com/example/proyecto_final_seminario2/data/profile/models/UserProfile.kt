package com.example.proyecto_final_seminario2.data.profile.models

import com.example.proyecto_final_seminario2.data.auth.models.AuthUser

data class UserProfile(
    val user: AuthUser,
    val totalReviews: Int,
    val reviews: List<ProfileReviewItem>
)