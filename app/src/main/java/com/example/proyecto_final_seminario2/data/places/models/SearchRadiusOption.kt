package com.example.proyecto_final_seminario2.data.places.models


enum class SearchRadiusOption(
    val label: String,
    val kilometers: Int,
    val meters: Double
) {
    ONE_KM(
        label = "1 km",
        kilometers = 1,
        meters = 1_000.0
    ),
    THREE_KM(
        label = "3 km",
        kilometers = 3,
        meters = 3_000.0
    ),
    FIVE_KM(
        label = "5 km",
        kilometers = 5,
        meters = 5_000.0
    ),
    TEN_KM(
        label = "10 km",
        kilometers = 10,
        meters = 10_000.0
    )
}