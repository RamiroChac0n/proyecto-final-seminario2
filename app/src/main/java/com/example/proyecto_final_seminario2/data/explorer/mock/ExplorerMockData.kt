package com.example.proyecto_final_seminario2.data.explorer.mock

import com.example.proyecto_final_seminario2.data.explorer.models.Business
import com.example.proyecto_final_seminario2.data.explorer.models.BusinessCategory

object ExplorerMockData {
    val businesses = listOf(
        Business(
            id = "restaurant-taqueria-central",
            name = "La Taquería Central",
            category = BusinessCategory.RESTAURANTS,
            locationCode = "Q25-Q49",
            distance = "1.2 km",
            rating = 4.8f,
            recommendationPercent = 95,
            imageUrl = "https://images.unsplash.com/photo-1504674900247-0877df9cc836?auto=format&fit=crop&w=900&q=80",
            tags = listOf("Precio accesible", "Puntualidad")
        ),
        Business(
            id = "pharmacy-pueblo",
            name = "Farmacia El Pueblo",
            category = BusinessCategory.PHARMACY,
            locationCode = "Q10-Q18",
            distance = "850 m",
            rating = 4.2f,
            recommendationPercent = 80,
            imageUrl = "https://images.unsplash.com/photo-1587854692152-cbe660dbde88?auto=format&fit=crop&w=900&q=80",
            tags = listOf("Higiene adecuada")
        ),
        Business(
            id = "hardware-don-juan",
            name = "Herramientas Don Juan",
            category = BusinessCategory.HARDWARE,
            locationCode = "Q9-Q300",
            distance = "2.0 km",
            rating = 4.9f,
            recommendationPercent = 85,
            imageUrl = "https://images.unsplash.com/photo-1581094794329-c8112a89af12?auto=format&fit=crop&w=900&q=80",
            tags = listOf("Trato amable", "Puntualidad")
        ),
        Business(
            id = "restaurant-corner-grill",
            name = "Corner Grill",
            category = BusinessCategory.RESTAURANTS,
            locationCode = "Q12-Q27",
            distance = "2.4 km",
            rating = 4.6f,
            recommendationPercent = 88,
            imageUrl = "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?auto=format&fit=crop&w=900&q=80",
            tags = listOf("Atención rápida", "Ambiente familiar")
        ),
        Business(
            id = "pharmacy-salud-plus",
            name = "Farmacia Salud Plus",
            category = BusinessCategory.PHARMACY,
            locationCode = "Q2-Q11",
            distance = "540 m",
            rating = 4.7f,
            recommendationPercent = 90,
            imageUrl = "https://images.unsplash.com/photo-1585435557343-3b092031f4d4?auto=format&fit=crop&w=900&q=80",
            tags = listOf("Farmacéutico disponible", "Buen stock")
        )
    )
}

