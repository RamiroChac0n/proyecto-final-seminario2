package com.example.proyecto_final_seminario2.data.explorer.models

enum class BusinessCategory(val label: String) {
    ALL("Todos"),
    RESTAURANTS("Restaurantes"),
    PHARMACY("Farmacia"),
    HARDWARE("Ferretería")
}

data class Business(
    val id: String,
    val name: String,
    val category: BusinessCategory,
    val locationCode: String,
    val distance: String,
    val rating: Float,
    val recommendationPercent: Int,
    val imageUrl: String,
    val tags: List<String>
)

