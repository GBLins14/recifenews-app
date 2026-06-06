package com.recifeemalerta.app.feature.map.domain.model

data class MapCoordinate(
    val latitude: Double,
    val longitude: Double
)

enum class MapIncidentType(
    val id: String,
    val label: String
) {
    Traffic(id = "traffic", label = "Trânsito"),
    Rain(id = "rain", label = "Chuva"),
    Security(id = "security", label = "Segurança"),
    Event(id = "event", label = "Eventos");

    companion object {
        fun fromId(id: String): MapIncidentType? {
            return entries.firstOrNull { it.id == id }
        }
    }
}

data class MapFilter(
    val id: String?,
    val label: String
)

data class MapIncident(
    val id: String,
    val title: String,
    val description: String,
    val neighborhood: String,
    val timeLabel: String,
    val type: MapIncidentType,
    val coordinate: MapCoordinate,
    val radiusMeters: Double,
    val severityLabel: String
)

data class LiveMapSnapshot(
    val center: MapCoordinate,
    val zoom: Int,
    val filters: List<MapFilter>,
    val incidents: List<MapIncident>
)
