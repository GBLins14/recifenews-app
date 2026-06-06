package com.recifeemalerta.app.ui.screens.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.recifeemalerta.app.ui.components.AppFeedback
import com.recifeemalerta.app.ui.screens.home.HomeBlue
import com.recifeemalerta.app.ui.screens.home.homePalette

@Composable
internal fun FeedFeedback(
    message: String,
    onClose: () -> Unit
) {
    AppFeedback(message = message, onClose = onClose, color = HomeBlue)
}

@Composable
internal fun SectionTitle(
    title: String,
    subtitle: String
) {
    val colors = homePalette()

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                color = colors.textPrimary
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = colors.textSecondary
            )
        }
    }
}

@Composable
internal fun EmptyFeedState(searchQuery: String, neighborhood: String) {
    val colors = homePalette()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(colors.card)
            .border(BorderStroke(1.dp, colors.cardBorder), RoundedCornerShape(18.dp))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Nada encontrado em $neighborhood",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
            color = colors.textPrimary
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = if (searchQuery.isBlank()) "Novos posts aparecerão aqui." else "Tente buscar por outro termo.",
            style = MaterialTheme.typography.bodyMedium,
            color = colors.textSecondary
        )
    }
}
