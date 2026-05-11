package com.example.proyecto_final_seminario2.data.explorer.repositories

import com.example.proyecto_final_seminario2.data.explorer.models.Business

interface ExplorerRepository {
    suspend fun getBusinesses(): List<Business>
}

