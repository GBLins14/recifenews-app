package com.recifenews.app.feature.neighborhood.data

import com.recifenews.app.core.location.recifeNeighborhoods
import com.recifenews.app.feature.neighborhood.domain.model.Neighborhood
import com.recifenews.app.feature.neighborhood.domain.model.NeighborhoodCatalog
import com.recifenews.app.feature.neighborhood.domain.repository.NeighborhoodRepository

class InMemoryNeighborhoodRepository : NeighborhoodRepository {
    override fun catalog(): NeighborhoodCatalog {
        return NeighborhoodCatalog(
            neighborhoods = recifeNeighborhoods.map { Neighborhood(it) }
        )
    }
}
