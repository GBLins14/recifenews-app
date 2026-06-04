package com.recifenews.app.feature.map.ui.state

import com.recifenews.app.feature.map.domain.model.LiveMapSnapshot
import com.recifenews.app.feature.map.domain.model.MapCoordinate
import com.recifenews.app.feature.map.domain.model.MapFilter
import com.recifenews.app.feature.map.domain.model.MapIncident

data class LiveMapUiState(
    val center: MapCoordinate,
    val zoom: Int,
    val filters: List<MapFilter>,
    val incidents: List<MapIncident>,
    val selectedFilterId: String? = null,
    val selectedIncidentId: String? = null,
    val searchQuery: String = "",
    val showSearch: Boolean = false
) {
    val visibleIncidents: List<MapIncident>
        get() {
            return incidents
                .filter { incident -> selectedFilterId == null || incident.type.id == selectedFilterId }
                .filter { incident ->
                    if (searchQuery.isBlank()) return@filter true
                    val searchableText = "${incident.title} ${incident.description} ${incident.neighborhood} ${incident.type.label}"
                    searchableText.contains(searchQuery, ignoreCase = true)
                }
        }

    val selectedIncident: MapIncident?
        get() = visibleIncidents.firstOrNull { it.id == selectedIncidentId } ?: visibleIncidents.firstOrNull()

    companion object {
        fun from(snapshot: LiveMapSnapshot): LiveMapUiState {
            return LiveMapUiState(
                center = snapshot.center,
                zoom = snapshot.zoom,
                filters = snapshot.filters,
                incidents = snapshot.incidents,
                selectedIncidentId = snapshot.incidents.firstOrNull()?.id
            )
        }
    }
}
