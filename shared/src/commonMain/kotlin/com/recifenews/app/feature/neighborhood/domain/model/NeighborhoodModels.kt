package com.recifenews.app.feature.neighborhood.domain.model

data class Neighborhood(
    val name: String
)

data class NeighborhoodCatalog(
    val neighborhoods: List<Neighborhood>
)
