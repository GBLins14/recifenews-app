package com.recifeemalerta.app.ui.screens.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.recifeemalerta.app.feature.map.domain.model.MapCoordinate
import com.recifeemalerta.app.feature.map.domain.model.MapIncident

@Composable
internal expect fun LiveMapRenderer(
    center: MapCoordinate,
    zoom: Int,
    incidents: List<MapIncident>,
    modifier: Modifier = Modifier
)
