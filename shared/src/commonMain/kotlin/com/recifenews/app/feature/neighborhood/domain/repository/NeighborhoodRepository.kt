package com.recifenews.app.feature.neighborhood.domain.repository

import com.recifenews.app.feature.neighborhood.domain.model.NeighborhoodCatalog

interface NeighborhoodRepository {
    fun catalog(): NeighborhoodCatalog
}
