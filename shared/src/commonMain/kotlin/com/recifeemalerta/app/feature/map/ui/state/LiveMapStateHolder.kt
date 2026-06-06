package com.recifeemalerta.app.feature.map.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.recifeemalerta.app.feature.map.domain.repository.LiveMapRepository

class LiveMapStateHolder(
    private val repository: LiveMapRepository
) {
    var state by mutableStateOf(LiveMapUiState.from(repository.loadLiveMap()))
        private set

    fun selectFilter(filterId: String?) {
        val nextFilterId = if (state.selectedFilterId == filterId) null else filterId
        val nextVisible = state.copy(selectedFilterId = nextFilterId).visibleIncidents

        state = state.copy(
            selectedFilterId = nextFilterId,
            selectedIncidentId = nextVisible.firstOrNull()?.id
        )
    }

    fun selectIncident(incidentId: String) {
        state = state.copy(selectedIncidentId = incidentId)
    }

    fun toggleSearch() {
        val nextVisibility = !state.showSearch
        state = state.copy(
            showSearch = nextVisibility,
            searchQuery = if (nextVisibility) state.searchQuery else ""
        )
    }

    fun onSearchChanged(query: String) {
        val nextState = state.copy(searchQuery = query, showSearch = true)
        state = nextState.copy(
            selectedIncidentId = nextState.visibleIncidents.firstOrNull()?.id
        )
    }

    fun refresh() {
        state = LiveMapUiState.from(repository.loadLiveMap())
    }
}
