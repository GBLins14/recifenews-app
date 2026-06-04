package com.recifenews.app.ui.screens.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.recifenews.app.feature.map.domain.model.MapCoordinate
import com.recifenews.app.feature.map.domain.model.MapIncident

@Composable
internal expect fun LiveMapRenderer(
    center: MapCoordinate,
    zoom: Int,
    incidents: List<MapIncident>,
    modifier: Modifier = Modifier
)
