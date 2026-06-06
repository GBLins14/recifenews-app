package com.recifeemalerta.app.feature.neighborhood.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.recifeemalerta.app.feature.neighborhood.domain.model.Neighborhood
import com.recifeemalerta.app.feature.neighborhood.domain.repository.NeighborhoodRepository

data class MainNeighborhoodUiState(
    val query: String = "",
    val neighborhoods: List<Neighborhood>,
    val selectedNeighborhood: String? = null
) {
    val filteredNeighborhoods: List<Neighborhood>
        get() = neighborhoods.filter { it.name.contains(query, ignoreCase = true) }
}

data class FollowNeighborhoodsUiState(
    val mainNeighborhood: String,
    val query: String = "",
    val neighborhoods: List<Neighborhood>,
    val selectedNeighborhoods: Set<String> = emptySet()
) {
    val filteredNeighborhoods: List<Neighborhood>
        get() = neighborhoods.filter { it.name.contains(query, ignoreCase = true) }
}

class MainNeighborhoodStateHolder(
    repository: NeighborhoodRepository
) {
    var state by mutableStateOf(MainNeighborhoodUiState(neighborhoods = repository.catalog().neighborhoods))
        private set

    fun onQueryChanged(query: String) {
        state = state.copy(query = query)
    }

    fun selectNeighborhood(neighborhood: String) {
        state = state.copy(selectedNeighborhood = neighborhood)
    }
}

class FollowNeighborhoodsStateHolder(
    mainNeighborhood: String,
    repository: NeighborhoodRepository
) {
    var state by mutableStateOf(
        FollowNeighborhoodsUiState(
            mainNeighborhood = mainNeighborhood,
            neighborhoods = repository.catalog().neighborhoods
        )
    )
        private set

    fun onQueryChanged(query: String) {
        state = state.copy(query = query)
    }

    fun toggleNeighborhood(neighborhood: String) {
        val selected = state.selectedNeighborhoods
        state = state.copy(
            selectedNeighborhoods = if (neighborhood in selected) {
                selected - neighborhood
            } else {
                selected + neighborhood
            }
        )
    }
}
