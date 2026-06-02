package com.recifenews.app.ui.screens.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.recifenews.app.feature.home.domain.model.UserProfile
import com.recifenews.app.ui.screens.home.HomeBlue
import com.recifenews.app.ui.screens.home.homePalette
import com.recifenews.app.ui.theme.AppColors

@Composable
internal fun NeighborhoodPickerCard(
    user: UserProfile,
    neighborhoods: List<String>,
    selectedNeighborhood: String,
    onNeighborhoodSelected: (String) -> Unit
) {
    val colors = homePalette()

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = colors.card),
        border = BorderStroke(1.dp, colors.cardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "Selecionar bairro",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                color = colors.textPrimary
            )
            Spacer(modifier = Modifier.height(12.dp))

            NeighborhoodSection(
                title = "Bairro principal",
                neighborhoods = listOf(user.mainNeighborhood),
                selectedNeighborhood = selectedNeighborhood,
                onNeighborhoodSelected = onNeighborhoodSelected
            )

            NeighborhoodSection(
                title = "Bairros seguidos",
                neighborhoods = user.followedNeighborhoods,
                selectedNeighborhood = selectedNeighborhood,
                onNeighborhoodSelected = onNeighborhoodSelected
            )

            NeighborhoodSection(
                title = "Todos os bairros",
                neighborhoods = neighborhoods,
                selectedNeighborhood = selectedNeighborhood,
                onNeighborhoodSelected = onNeighborhoodSelected
            )
        }
    }
}

@Composable
private fun NeighborhoodSection(
    title: String,
    neighborhoods: List<String>,
    selectedNeighborhood: String,
    onNeighborhoodSelected: (String) -> Unit
) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Black),
        color = HomeBlue
    )
    Spacer(modifier = Modifier.height(8.dp))

    neighborhoods.chunked(2).forEach { rowItems ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            rowItems.forEach { neighborhood ->
                NeighborhoodButton(
                    neighborhood = neighborhood,
                    selected = selectedNeighborhood == neighborhood,
                    onClick = { onNeighborhoodSelected(neighborhood) },
                    modifier = Modifier.weight(1f)
                )
            }
            if (rowItems.size == 1) {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun NeighborhoodButton(
    neighborhood: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = homePalette()

    Box(
        modifier = modifier
            .height(38.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(if (selected) colors.accentStrong else colors.subtleSurface)
            .border(
                BorderStroke(1.dp, if (selected) colors.accentStrong else colors.cardBorder),
                RoundedCornerShape(14.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = neighborhood,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = if (selected) AppColors.White else colors.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
