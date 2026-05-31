package com.recifenews.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.recifenews.app.ui.theme.AppColors

@Composable
fun PaginationDots(
    total: Int,
    current: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(total) { index ->
            val targetColor = if (index == current) {
                if (current == 0 && isSystemInDarkTheme() == false) {
                    AppColors.White.copy(alpha = 0.75f)
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                }
            } else {
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            }
            val color = animateColorAsState(targetColor).value
            val width = animateDpAsState(if (index == current) 16.dp else 8.dp).value
            
            Box(
                modifier = Modifier
                    .width(width)
                    .size(height = 8.dp, width = width)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}
