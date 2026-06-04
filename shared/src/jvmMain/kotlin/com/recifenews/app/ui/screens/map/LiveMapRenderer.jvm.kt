package com.recifenews.app.ui.screens.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.recifenews.app.feature.map.domain.model.MapCoordinate
import com.recifenews.app.feature.map.domain.model.MapIncident

@Composable
internal actual fun LiveMapRenderer(
    center: MapCoordinate,
    zoom: Int,
    incidents: List<MapIncident>,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Mapa disponível no Android e iOS",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
