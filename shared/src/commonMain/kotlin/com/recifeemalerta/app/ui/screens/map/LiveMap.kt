package com.recifeemalerta.app.ui.screens.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.recifeemalerta.app.feature.map.domain.model.MapFilter
import com.recifeemalerta.app.feature.map.domain.model.MapIncident
import com.recifeemalerta.app.feature.map.domain.model.MapIncidentType
import com.recifeemalerta.app.feature.map.ui.state.LiveMapUiState
import com.recifeemalerta.app.ui.icons.AppIcons
import com.recifeemalerta.app.ui.screens.home.homePalette
import com.recifeemalerta.app.ui.theme.AlertEvent
import com.recifeemalerta.app.ui.theme.AlertRain
import com.recifeemalerta.app.ui.theme.AlertSecurity
import com.recifeemalerta.app.ui.theme.AlertTraffic

@Composable
internal fun LiveMapPanel(
    state: LiveMapUiState,
    actions: LiveMapActions,
    modifier: Modifier = Modifier
) {
    val colors = homePalette()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.feedBackground)
    ) {
        LiveMapTopBar(
            onSearchClick = actions.onSearchToggle,
            onLayersClick = actions.onRefresh
        )

        AnimatedVisibility(visible = state.showSearch) {
            LiveMapSearchField(
                query = state.searchQuery,
                onQueryChange = actions.onSearchChange
            )
        }

        LiveMapFilterStrip(
            filters = state.filters,
            selectedFilterId = state.selectedFilterId,
            onFilterSelected = actions.onFilterSelected
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFFD7ECF7))
        ) {
            LiveMapRenderer(
                center = state.center,
                zoom = state.zoom,
                incidents = state.visibleIncidents,
                modifier = Modifier.fillMaxSize()
            )

            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 18.dp, end = 14.dp)
                    .size(46.dp)
                    .clip(CircleShape)
                    .clickable(onClick = actions.onRefresh),
                shape = CircleShape,
                color = colors.card,
                contentColor = colors.textPrimary,
                shadowElevation = 8.dp,
                border = BorderStroke(1.dp, colors.cardBorder)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = AppIcons.Layers,
                        contentDescription = "Camadas",
                        modifier = Modifier.size(23.dp)
                    )
                }
            }

            state.selectedIncident?.let { incident ->
                LiveMapIncidentCard(
                    incident = incident,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 14.dp, vertical = 14.dp)
                )
            }

            if (state.visibleIncidents.isEmpty()) {
                LiveMapEmptyState(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
private fun LiveMapTopBar(
    onSearchClick: () -> Unit,
    onLayersClick: () -> Unit
) {
    val colors = homePalette()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 12.dp, end = 14.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Mapa ao vivo",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
            color = colors.textPrimary,
            modifier = Modifier.weight(1f)
        )

        HeaderActionButton(
            icon = AppIcons.Search,
            contentDescription = "Buscar no mapa",
            onClick = onSearchClick
        )
        Spacer(modifier = Modifier.width(8.dp))
        HeaderActionButton(
            icon = AppIcons.Layers,
            contentDescription = "Camadas do mapa",
            onClick = onLayersClick
        )
    }
}

@Composable
private fun HeaderActionButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    val colors = homePalette()

    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = colors.textPrimary,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun LiveMapSearchField(
    query: String,
    onQueryChange: (String) -> Unit
) {
    val colors = homePalette()

    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 4.dp),
        singleLine = true,
        shape = RoundedCornerShape(18.dp),
        placeholder = {
            Text(
                text = "Buscar ocorrência ou bairro",
                color = colors.textMuted
            )
        },
        leadingIcon = {
            Icon(
                imageVector = AppIcons.Search,
                contentDescription = null,
                tint = colors.textMuted
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = colors.card,
            unfocusedContainerColor = colors.card,
            focusedBorderColor = colors.accent,
            unfocusedBorderColor = colors.cardBorder,
            cursorColor = colors.accent,
            focusedTextColor = colors.textPrimary,
            unfocusedTextColor = colors.textPrimary
        )
    )
}

@Composable
private fun LiveMapFilterStrip(
    filters: List<MapFilter>,
    selectedFilterId: String?,
    onFilterSelected: (String?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(start = 14.dp, end = 14.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEach { filter ->
            LiveMapFilterChip(
                filter = filter,
                selected = filter.id == selectedFilterId,
                onClick = { onFilterSelected(filter.id) }
            )
        }
    }
}

@Composable
private fun LiveMapFilterChip(
    filter: MapFilter,
    selected: Boolean,
    onClick: () -> Unit
) {
    val colors = homePalette()
    val containerColor = if (selected) colors.accentStrong else colors.subtleSurface
    val contentColor = if (selected) colors.onAccent else colors.textSecondary

    Surface(
        modifier = Modifier
            .height(38.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        color = containerColor,
        border = BorderStroke(1.dp, if (selected) colors.accentStrong else colors.cardBorder)
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = filter.label,
                color = contentColor,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                maxLines = 1
            )
        }
    }
}

@Composable
private fun LiveMapIncidentCard(
    incident: MapIncident,
    modifier: Modifier = Modifier
) {
    val colors = homePalette()

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = colors.card.copy(alpha = 0.96f),
        tonalElevation = 0.dp,
        shadowElevation = 14.dp,
        border = BorderStroke(1.dp, colors.cardBorder)
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
            verticalAlignment = Alignment.Top
        ) {
            MapIncidentIcon(type = incident.type)
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = incident.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = colors.textPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = incident.timeLabel,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = colors.textMuted,
                        maxLines = 1
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = incident.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.textSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${incident.neighborhood} · ${incident.severityLabel}",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = incidentColor(incident.type)
                )
            }
        }
    }
}

@Composable
private fun MapIncidentIcon(type: MapIncidentType) {
    val color = incidentColor(type)
    val icon = when (type) {
        MapIncidentType.Traffic -> AppIcons.DirectionsBus
        MapIncidentType.Rain -> AppIcons.MyLocation
        MapIncidentType.Security -> AppIcons.Security
        MapIncidentType.Event -> AppIcons.Event
    }

    Surface(
        modifier = Modifier.size(42.dp),
        shape = CircleShape,
        color = color.copy(alpha = 0.12f),
        contentColor = color
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun LiveMapEmptyState(
    modifier: Modifier = Modifier
) {
    val colors = homePalette()

    Surface(
        modifier = modifier.padding(20.dp),
        shape = RoundedCornerShape(18.dp),
        color = colors.card.copy(alpha = 0.94f),
        border = BorderStroke(1.dp, colors.cardBorder)
    ) {
        Text(
            text = "Nenhuma ocorrência encontrada.",
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp),
            color = colors.textSecondary,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}

@Composable
private fun incidentColor(type: MapIncidentType): Color {
    return when (type) {
        MapIncidentType.Traffic -> AlertTraffic
        MapIncidentType.Rain -> AlertRain
        MapIncidentType.Security -> AlertSecurity
        MapIncidentType.Event -> AlertEvent
    }
}

internal data class LiveMapActions(
    val onFilterSelected: (String?) -> Unit,
    val onSearchToggle: () -> Unit,
    val onSearchChange: (String) -> Unit,
    val onRefresh: () -> Unit
)
