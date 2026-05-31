package com.recifenews.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.recifenews.app.ui.theme.AppColors

@Composable
fun AppFeedback(
    message: String,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = AppColors.ButtonBlue
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(color.copy(alpha = 0.1f))
            .border(BorderStroke(1.dp, color.copy(alpha = 0.16f)), RoundedCornerShape(14.dp))
            .padding(horizontal = 14.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = color,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "OK",
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Black),
            color = color,
            modifier = Modifier.clickable(onClick = onClose)
        )
    }
}
