package com.example.proyecto_final_seminario2.data.explorer.repositories

import com.example.proyecto_final_seminario2.data.explorer.mock.ExplorerMockData
import com.example.proyecto_final_seminario2.data.explorer.models.Business

class MockExplorerRepository : ExplorerRepository {
    override suspend fun getBusinesses(): List<Business> = ExplorerMockData.businesses
}

