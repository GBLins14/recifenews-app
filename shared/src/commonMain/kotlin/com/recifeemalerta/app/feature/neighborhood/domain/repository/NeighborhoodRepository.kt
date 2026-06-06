package com.recifeemalerta.app.feature.neighborhood.domain.repository

import com.recifeemalerta.app.feature.neighborhood.domain.model.NeighborhoodCatalog

interface NeighborhoodRepository {
    fun catalog(): NeighborhoodCatalog
}
