package com.recifeemalerta.app.feature.map.data

import com.recifeemalerta.app.feature.map.domain.model.LiveMapSnapshot
import com.recifeemalerta.app.feature.map.domain.model.MapCoordinate
import com.recifeemalerta.app.feature.map.domain.model.MapFilter
import com.recifeemalerta.app.feature.map.domain.model.MapIncident
import com.recifeemalerta.app.feature.map.domain.model.MapIncidentType
import com.recifeemalerta.app.feature.map.domain.repository.LiveMapRepository

class InMemoryLiveMapRepository : LiveMapRepository {
    override fun loadLiveMap(): LiveMapSnapshot {
        return LiveMapSnapshot(
            center = MapCoordinate(latitude = -8.0631, longitude = -34.9009),
            zoom = 12,
            filters = listOf(
                MapFilter(id = null, label = "Todos"),
                MapFilter(id = MapIncidentType.Traffic.id, label = MapIncidentType.Traffic.label),
                MapFilter(id = MapIncidentType.Rain.id, label = MapIncidentType.Rain.label),
                MapFilter(id = MapIncidentType.Security.id, label = MapIncidentType.Security.label),
                MapFilter(id = MapIncidentType.Event.id, label = MapIncidentType.Event.label)
            ),
            incidents = listOf(
                MapIncident(
                    id = "rain-boa-viagem",
                    title = "Chuva forte em Boa Viagem",
                    description = "Previsão de chuva forte nas próximas horas.",
                    neighborhood = "Boa Viagem",
                    timeLabel = "Agora",
                    type = MapIncidentType.Rain,
                    coordinate = MapCoordinate(latitude = -8.1231, longitude = -34.9004),
                    radiusMeters = 780.0,
                    severityLabel = "Atenção"
                ),
                MapIncident(
                    id = "flood-afogados",
                    title = "Alagamento em Afogados",
                    description = "Ponto de alagamento próximo ao eixo principal. Evite a área.",
                    neighborhood = "Afogados",
                    timeLabel = "6 min",
                    type = MapIncidentType.Rain,
                    coordinate = MapCoordinate(latitude = -8.0747, longitude = -34.9096),
                    radiusMeters = 620.0,
                    severityLabel = "Perigo"
                ),
                MapIncident(
                    id = "security-santo-amaro",
                    title = "Assalto relatado em Santo Amaro",
                    description = "Moradores registraram movimentação suspeita. Redobre atenção.",
                    neighborhood = "Santo Amaro",
                    timeLabel = "12 min",
                    type = MapIncidentType.Security,
                    coordinate = MapCoordinate(latitude = -8.0486, longitude = -34.8847),
                    radiusMeters = 520.0,
                    severityLabel = "Alerta"
                ),
                MapIncident(
                    id = "traffic-derby",
                    title = "Trânsito lento no Derby",
                    description = "Retenção na Agamenon sentido Boa Viagem.",
                    neighborhood = "Derby",
                    timeLabel = "18 min",
                    type = MapIncidentType.Traffic,
                    coordinate = MapCoordinate(latitude = -8.0554, longitude = -34.8989),
                    radiusMeters = 460.0,
                    severityLabel = "Moderado"
                ),
                MapIncident(
                    id = "event-recife-antigo",
                    title = "Evento no Recife Antigo",
                    description = "Movimento intenso no entorno do Marco Zero.",
                    neighborhood = "Bairro do Recife",
                    timeLabel = "25 min",
                    type = MapIncidentType.Event,
                    coordinate = MapCoordinate(latitude = -8.0633, longitude = -34.8711),
                    radiusMeters = 440.0,
                    severityLabel = "Ao vivo"
                ),
                MapIncident(
                    id = "security-casa-amarela",
                    title = "Ocorrência em Casa Amarela",
                    description = "Relato de tentativa de assalto perto da via principal.",
                    neighborhood = "Casa Amarela",
                    timeLabel = "32 min",
                    type = MapIncidentType.Security,
                    coordinate = MapCoordinate(latitude = -8.0262, longitude = -34.9172),
                    radiusMeters = 540.0,
                    severityLabel = "Atenção"
                ),
                MapIncident(
                    id = "traffic-pina",
                    title = "Fluxo intenso no Pina",
                    description = "Lentidão perto da saída para Boa Viagem.",
                    neighborhood = "Pina",
                    timeLabel = "41 min",
                    type = MapIncidentType.Traffic,
                    coordinate = MapCoordinate(latitude = -8.0921, longitude = -34.8856),
                    radiusMeters = 390.0,
                    severityLabel = "Trânsito"
                )
            )
        )
    }
}
