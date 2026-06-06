package com.recifeemalerta.app.feature.neighborhood.data

import com.recifeemalerta.app.core.location.recifeNeighborhoods
import com.recifeemalerta.app.feature.neighborhood.domain.model.Neighborhood
import com.recifeemalerta.app.feature.neighborhood.domain.model.NeighborhoodCatalog
import com.recifeemalerta.app.feature.neighborhood.domain.repository.NeighborhoodRepository

class InMemoryNeighborhoodRepository : NeighborhoodRepository {
    override fun catalog(): NeighborhoodCatalog {
        return NeighborhoodCatalog(
            neighborhoods = recifeNeighborhoods.map { Neighborhood(it) }
        )
    }
}
