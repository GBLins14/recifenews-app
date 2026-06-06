package com.recifeemalerta.app.ui.screens.map

import com.recifeemalerta.app.feature.map.domain.model.MapCoordinate
import com.recifeemalerta.app.feature.map.domain.model.MapIncident
import com.recifeemalerta.app.feature.map.domain.model.MapIncidentType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
private data class MapHtmlPayload(
    val centerLat: Double,
    val centerLng: Double,
    val zoom: Int,
    val incidents: List<MapHtmlIncident>
)

@Serializable
private data class MapHtmlIncident(
    val id: String,
    val title: String,
    val description: String,
    val neighborhood: String,
    val timeLabel: String,
    val type: String,
    val color: String,
    val lat: Double,
    val lng: Double,
    val radiusMeters: Double
)

internal fun buildLiveMapHtml(
    center: MapCoordinate,
    zoom: Int,
    incidents: List<MapIncident>
): String {
    val payload = MapHtmlPayload(
        centerLat = center.latitude,
        centerLng = center.longitude,
        zoom = zoom,
        incidents = incidents.map { incident ->
            MapHtmlIncident(
                id = incident.id,
                title = incident.title,
                description = incident.description,
                neighborhood = incident.neighborhood,
                timeLabel = incident.timeLabel,
                type = incident.type.id,
                color = mapIncidentColor(incident.type),
                lat = incident.coordinate.latitude,
                lng = incident.coordinate.longitude,
                radiusMeters = incident.radiusMeters
            )
        }
    )
    val json = Json.encodeToString(payload)

    return """
        <!doctype html>
        <html>
        <head>
          <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
          <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />
          <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
          <style>
            html, body, #map {
              width: 100%;
              height: 100%;
              margin: 0;
              padding: 0;
              overflow: hidden;
              background: #d8ecf7;
              font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
            }
            .leaflet-control-attribution {
              font-size: 9px;
              color: #64748B;
              background: rgba(255, 255, 255, 0.72);
            }
            .rn-popup-title {
              font-weight: 800;
              color: #0F172A;
              font-size: 14px;
              margin-bottom: 4px;
            }
            .rn-popup-text {
              color: #475569;
              font-size: 12px;
              line-height: 1.35;
            }
          </style>
        </head>
        <body>
          <div id="map"></div>
          <script>
            const payload = $json;
            const map = L.map("map", {
              zoomControl: false,
              attributionControl: true,
              dragging: true,
              tap: true,
              zoomAnimation: false,
              markerZoomAnimation: false,
              fadeAnimation: false
            }).setView([payload.centerLat, payload.centerLng], payload.zoom);

            const transparentTile = "data:image/gif;base64,R0lGODlhAQABAAD/ACwAAAAAAQABAAACADs=";
            const primaryTiles = L.tileLayer("https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png", {
              subdomains: "abcd",
              maxZoom: 20,
              detectRetina: true,
              errorTileUrl: transparentTile,
              attribution: "© OpenStreetMap © CARTO"
            }).addTo(map);

            let fallbackLoaded = false;
            primaryTiles.on("tileerror", () => {
              if (fallbackLoaded) return;
              fallbackLoaded = true;
              L.tileLayer("https://server.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer/tile/{z}/{y}/{x}", {
                maxZoom: 19,
                errorTileUrl: transparentTile,
                attribution: "Tiles © Esri"
              }).addTo(map);
            });

            function createPinIcon(color) {
              const svg = `
                <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 24 24" fill="none">
                  <defs>
                    <filter id="pinShadow" x="1" y="1" width="22" height="22" filterUnits="userSpaceOnUse">
                      <feDropShadow dx="0" dy="1.4" stdDeviation="1.25" flood-color="#0F172A" flood-opacity="0.24"/>
                    </filter>
                  </defs>
                  <path filter="url(#pinShadow)" d="M12 2C8.13 2 5 5.13 5 9C5 14.25 12 22 12 22C12 22 19 14.25 19 9C19 5.13 15.87 2 12 2Z" fill="#FFFFFF"/>
                  <path d="M12 3.55C8.98 3.55 6.55 5.98 6.55 9C6.55 12.7 10.58 17.92 12 19.62C13.42 17.92 17.45 12.7 17.45 9C17.45 5.98 15.02 3.55 12 3.55Z" fill="${'$'}{color}"/>
                  <circle cx="12" cy="9" r="2.45" fill="#FFFFFF"/>
                </svg>
              `.trim();

              return L.icon({
                iconUrl: "data:image/svg+xml;charset=UTF-8," + encodeURIComponent(svg),
                iconSize: [40, 40],
                iconAnchor: [20, 37],
                popupAnchor: [0, -34]
              });
            }

            payload.incidents.forEach((incident) => {
              const position = [incident.lat, incident.lng];

              L.circle(position, {
                radius: incident.radiusMeters,
                color: incident.color,
                fillColor: incident.color,
                fillOpacity: 0.18,
                opacity: 0.55,
                weight: 2
              }).addTo(map);

              L.marker(position, {
                icon: createPinIcon(incident.color),
                zIndexOffset: 1000
              })
                .addTo(map)
                .bindPopup(`<div class="rn-popup-title">${'$'}{incident.title}</div><div class="rn-popup-text">${'$'}{incident.neighborhood} · ${'$'}{incident.timeLabel}<br>${'$'}{incident.description}</div>`);
            });
          </script>
        </body>
        </html>
    """.trimIndent()
}

internal fun mapIncidentColor(type: MapIncidentType): String {
    return when (type) {
        MapIncidentType.Traffic -> "#F97316"
        MapIncidentType.Rain -> "#0EA5E9"
        MapIncidentType.Security -> "#EF4444"
        MapIncidentType.Event -> "#22C55E"
    }
}
