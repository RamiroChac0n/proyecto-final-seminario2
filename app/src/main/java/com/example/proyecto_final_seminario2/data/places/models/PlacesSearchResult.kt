package com.example.proyecto_final_seminario2.data.places.models

import com.example.proyecto_final_seminario2.data.explorer.models.Business

data class PlacesSearchResult(
    val places: List<Business>,
    val isFromCache: Boolean
)